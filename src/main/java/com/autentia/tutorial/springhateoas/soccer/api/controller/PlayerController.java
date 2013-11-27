package com.autentia.tutorial.springhateoas.soccer.api.controller;

import com.autentia.tutorial.springhateoas.soccer.api.exception.InvalidInputDataException;
import com.autentia.tutorial.springhateoas.soccer.api.exception.ResourceNotFoundException;
import com.autentia.tutorial.springhateoas.soccer.dao.PlayerDao;
import com.autentia.tutorial.springhateoas.soccer.model.Player;
import com.autentia.tutorial.springhateoas.soccer.model.Players;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    private PlayerDao playerDao;

    @Autowired
    public PlayerController(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @RequestMapping(value = "/teams/{teamId}/players", method = RequestMethod.GET)
    public @ResponseBody Players getTeamPlayers(@PathVariable int teamId) {
        LOG.trace("Recibida solicitud para devolver todos los jugadores del equipo con id " + teamId);
        final List<Player> players = playerDao.getByTeamId(teamId);
        addPlayerLink(players);
        return new Players(players);
    }

    @RequestMapping(value = "/teams/{teamId}/players/{playerId}", method = RequestMethod.GET)
    public @ResponseBody Player getById(@PathVariable int teamId, @PathVariable int playerId) {
        LOG.trace("Recibida solicitud para devolver el jugador con id {} que juega en el equipo con id ", playerId, teamId);
        final Player player = playerDao.getById(playerId, teamId);
        if (player == null) {
            LOG.info("El jugador con id {} y que juega en el equipo {} no existe", playerId, teamId);
            throw new ResourceNotFoundException();
        }
        addPlayerLink(player);
        return player;
    }

    @RequestMapping(value = "/teams/{teamId}/players", method = RequestMethod.POST)
    public @ResponseBody HttpEntity<Player> create(@PathVariable int teamId, @RequestBody Player player) {

        player.setTeamId(teamId);
        LOG.trace("Recibida solicitud para crear el jugador {}", player);
        validateNewPlayer(player);

        try {
            player.setPlayerId(playerDao.persist(player));
            LOG.info("Jugador creado correctamente {}", player);
        } catch (IllegalArgumentException iae) {
            LOG.error("No se puede crear el jugador {}", player, iae);
            throw new InvalidInputDataException("Los datos del jugador son erroneos");
        }

        addPlayerLink(player);
        final HttpHeaders headers = createHeadersWithResourceLocation(teamId, player.getPlayerId());
        return new ResponseEntity<>(player, headers, HttpStatus.CREATED);
    }

    private void addPlayerLink(List<Player> players) {
        if (!CollectionUtils.isEmpty(players)) {
            for (Player player : players) {
                addPlayerLink(player);
            }
        }
    }

    private void addPlayerLink(Player player) {
        addSelfLink(player);
        addTeamLink(player);
    }

    private void addSelfLink(Player player) {
        player.add(linkTo(methodOn(PlayerController.class).getById(player.getTeamId(), player.getPlayerId())).withSelfRel());
    }

    private void addTeamLink(Player player) {
        player.add(linkTo(methodOn(TeamController.class).getById(player.getTeamId())).withRel("team"));
    }

    private void validateNewPlayer(Player player) {
        validateName(player.getName());
        validateGoals(player.getGoals());
        validateCountry(player.getCountry());
        validateAge(player.getAge());
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            final String errorMsg = "El nombre es obligatorio";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateGoals(int goals) {
        if (goals < 0) {
            final String errorMsg = "El número de goles no puede ser menor que 0";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateCountry(String country) {
        if (StringUtils.isBlank(country)) {
            final String errorMsg = "El pais es obligatorio";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateAge(int age) {
        if (age < 0) {
            final String errorMsg = "La edad del jugador no puede ser menor que 0";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private HttpHeaders createHeadersWithResourceLocation(int teamId, int playerId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(PlayerController.class).getById(teamId, playerId)).toUri());
        return headers;
    }

}
