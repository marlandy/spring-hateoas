package com.autentia.tutorial.springhateoas.soccer.api.controller;

import com.autentia.tutorial.springhateoas.soccer.api.exception.InvalidInputDataException;
import com.autentia.tutorial.springhateoas.soccer.api.exception.ResourceNotFoundException;
import com.autentia.tutorial.springhateoas.soccer.dao.TeamDao;
import com.autentia.tutorial.springhateoas.soccer.model.Team;
import com.autentia.tutorial.springhateoas.soccer.model.Teams;
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
public class TeamController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    private final TeamDao teamDao;

    @Autowired
    public TeamController(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public @ResponseBody Teams getAll() {
        LOG.trace("Recibida solicitud para devolver todos los equipos");
        final List<Team> teams = teamDao.getAll();
        addTeamLinks(teams);
        return new Teams(teams);
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET)
    public @ResponseBody Team getById(@PathVariable int id) {
        LOG.trace("Recibida solicitud para devolver el equipo con id {}", id);
        final Team team = teamDao.getById(id);
        if (team == null) {
            LOG.info("El equipo con id {} no existe", id);
            throw new ResourceNotFoundException();
        }
        addTeamLinks(team);
        return team;
    }


    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    public @ResponseBody HttpEntity<Team> create(@RequestBody Team team) {
        LOG.trace("Recibida solicitud para crear el equipo {}", team);
        validateNewTeam(team);

        try {
            team.setTeamId(teamDao.persist(team));
        } catch (IllegalArgumentException iae) {
            LOG.error("No se puede crear el equipo {}", team, iae);
            throw new InvalidInputDataException("Los datos del equipo son erroneos");
        }

        addSelfLink(team);
        final HttpHeaders headers = createHeadersWithResourceLocation(team.getTeamId());
        return new ResponseEntity<>(team, headers, HttpStatus.CREATED);
    }


    private void addTeamLinks(List<Team> teams) {
        if (!CollectionUtils.isEmpty(teams)) {
            for (Team team : teams) {
                addTeamLinks(team);
            }
        }
    }

    private void addTeamLinks(Team team) {
        addSelfLink(team);
        addStadiumLink(team);
        addPlayerLink(team);
    }

    private void addSelfLink(Team team) {
        team.add(linkTo(methodOn(TeamController.class).getById(team.getTeamId())).withSelfRel());
    }

    private void addStadiumLink(Team team) {
        team.add(linkTo(methodOn(StadiumController.class).getByTeamId(team.getTeamId())).withRel("stadium"));
    }

    private void addPlayerLink(Team team) {
        team.add(linkTo(methodOn(PlayerController.class).getTeamPlayers(team.getTeamId())).withRel("players"));
    }

    private void validateNewTeam(Team team) {
        validateFoundationYear(team.getFoundationYear());
        validateName(team.getName());
        validateRankingPosition(team.getRankingPosition());
    }

    private void validateFoundationYear(int foundationYear) {
        if (foundationYear <= 0) {
            final String errorMsg = "El año de fundación debe ser mayor que 0";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            final String errorMsg = "El nombre del equipo es requerido";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateRankingPosition(int rankingPosition) {
        if (rankingPosition <= 0) {
            final String errorMsg = "La posición en el ranking debe ser mayor que 0";
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
