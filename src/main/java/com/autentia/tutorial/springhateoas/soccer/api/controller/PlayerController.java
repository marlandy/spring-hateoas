package com.autentia.tutorial.springhateoas.soccer.api.controller;

import com.autentia.tutorial.springhateoas.soccer.api.exception.InvalidInputDataException;
import com.autentia.tutorial.springhateoas.soccer.api.exception.ResourceNotFoundException;
import com.autentia.tutorial.springhateoas.soccer.dao.PlayerDao;
import com.autentia.tutorial.springhateoas.soccer.model.Player;
import com.autentia.tutorial.springhateoas.soccer.model.PlayerShortInfo;
import com.autentia.tutorial.springhateoas.soccer.model.Players;
import com.autentia.tutorial.springhateoas.soccer.model.TeamShortInfo;
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
@RequestMapping(value = "/players")
public class PlayerController {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerController.class);

    private PlayerDao playerDao;

    @Autowired
    public PlayerController(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Players getAll() {
        LOG.trace("Recibida solicitud para devolver todos los jugadores");
        final List<Player> players = playerDao.getAll();
        addTeamLink(players);
        return new Players(players);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Player getById(@PathVariable int id) {
        LOG.trace("Recibida solicitud para devolver el jugador con id {}", id);
        final Player player = playerDao.getById(id);
        if (player == null) {
            LOG.info("El jugador con id {} no existe", id);
            throw new ResourceNotFoundException();
        }
        addTeamLink(player);
        return player;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody HttpEntity<PlayerShortInfo> create(@RequestBody Player player) {

        LOG.trace("Recibida solicitud para crear el jugador {}", player);
        validateNewPlayer(player);

        int newPlayerId;
        try {
            newPlayerId = playerDao.persist(player);
        } catch (IllegalArgumentException iae) {
            LOG.error("No se puede crear el jugador {}", player, iae);
            throw new InvalidInputDataException("Los datos del jugador son erroneos");
        }

        final PlayerShortInfo newPlayer = new PlayerShortInfo(newPlayerId, player.getName());
        newPlayer.add(linkTo(methodOn(PlayerController.class).getById(newPlayerId)).withSelfRel());

        final HttpHeaders headers = createHeadersWithResourceLocation(newPlayerId);
        return new ResponseEntity<>(newPlayer, headers, HttpStatus.CREATED);
    }

    private void addTeamLink(List<Player> players) {
        if (!CollectionUtils.isEmpty(players)) {
            for (Player player : players) {
                addTeamLink(player);
            }
        }
    }

    private void addTeamLink(Player player) {
        final TeamShortInfo team = player.getCurrentTeam();
        if (team != null) {
            team.add(linkTo(methodOn(TeamController.class).getById(team.getIdTeam())).withSelfRel());
        }
    }

    private void validateNewPlayer(Player player) {
        validateName(player.getName());
        validateGoals(player.getGoals());
        validateCountry(player.getCountry());
        validateAge(player.getAge());
        validateCurrentTeam(player.getCurrentTeam());
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

    private void validateCurrentTeam(TeamShortInfo currentTeam) {
        if (currentTeam == null || StringUtils.isEmpty(currentTeam.getName())) {
            final String errorMsg = "El equipo actual del jugador es obligatorio";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private HttpHeaders createHeadersWithResourceLocation(int playerId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(PlayerController.class).getById(playerId)).toUri());
        return headers;
    }

}
