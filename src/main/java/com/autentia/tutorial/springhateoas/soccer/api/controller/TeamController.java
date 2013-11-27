package com.autentia.tutorial.springhateoas.soccer.api.controller;

import com.autentia.tutorial.springhateoas.soccer.api.exception.InvalidInputDataException;
import com.autentia.tutorial.springhateoas.soccer.api.exception.ResourceNotFoundException;
import com.autentia.tutorial.springhateoas.soccer.dao.TeamDao;
import com.autentia.tutorial.springhateoas.soccer.model.*;
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
@RequestMapping(value = "/teams")
public class TeamController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    private final TeamDao teamDao;

    @Autowired
    public TeamController(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Teams getAll() {
        LOG.trace("Recibida solicitud para devolver todos los equipos");
        final List<Team> teams = teamDao.getAll();
        addStadiumAndPlayerLinks(teams);
        return new Teams(teams);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Team getById(@PathVariable int id) {
        LOG.trace("Recibida solicitud para devolver el equipo con id {}", id);
        final Team team = teamDao.getById(id);
        if (team == null) {
            LOG.info("El equipo con id {} no existe", id);
            throw new ResourceNotFoundException();
        }
        addStadiumAndPlayerLinks(team);
        return team;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody HttpEntity<TeamShortInfo> create(@RequestBody Team team) {

        LOG.trace("Recibida solicitud para crear el equipo {}", team);
        validateNewTeam(team);

        int newTeamId;
        try {
            newTeamId = teamDao.persist(team);
        } catch (IllegalArgumentException iae) {
            LOG.error("No se puede crear el equipo {}", team, iae);
            throw new InvalidInputDataException("Los datos del equipo son erroneos");
        }

        final TeamShortInfo newTeam = new TeamShortInfo(newTeamId, team.getName());
        newTeam.add(linkTo(methodOn(TeamController.class).getById(newTeamId)).withSelfRel());

        final HttpHeaders headers = createHeadersWithResourceLocation(newTeamId);
        return new ResponseEntity<>(newTeam, headers, HttpStatus.CREATED);
    }

    private void addStadiumAndPlayerLinks(List<Team> teams) {
        if (!CollectionUtils.isEmpty(teams)) {
            for (Team team : teams) {
                addStadiumAndPlayerLinks(team);
            }
        }
    }

    private void addStadiumAndPlayerLinks(Team team) {
        addStadiumLink(team);
        addPlayerLink(team);
    }

    private void addStadiumLink(Team team) {
        final StadiumShortInfo stadium = team.getStadium();
        if (stadium != null) {
            stadium.add(linkTo(methodOn(StadiumController.class).getById(stadium.getIdStadium())).withSelfRel());
        }
    }

    private void addPlayerLink(Team team) {
        final List<PlayerShortInfo> players = team.getPlayers();
        if (!CollectionUtils.isEmpty(players)) {
            for (PlayerShortInfo player : players) {
                player.add(linkTo(methodOn(PlayerController.class).getById(player.getIdPlayer())).withSelfRel());
            }
        }
    }

    private void validateNewTeam(Team team) {
        validateName(team.getName());
        validateFoundationYear(team.getFoundationYear());
        validateRankingPosition(team.getRankingPosition());
        validateStadium(team.getStadium());
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            final String errorMsg = "El nombre es obligatorio";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateFoundationYear(int foundationYear) {
        if (foundationYear < 0) {
            final String errorMsg = "El año de fundación no puede ser menor que 0";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateRankingPosition(int rankingPosition) {
        if (rankingPosition < 0) {
            final String errorMsg = "La posición en el ranking no puede ser menor que 0";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateStadium(StadiumShortInfo stadium) {
        if (stadium == null || StringUtils.isBlank(stadium.getName())) {
            final String errorMsg = "El estadio es obligatorio";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private HttpHeaders createHeadersWithResourceLocation(int teamId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(TeamController.class).getById(teamId)).toUri());
        return headers;
    }

}
