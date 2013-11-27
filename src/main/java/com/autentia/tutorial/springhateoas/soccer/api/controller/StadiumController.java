package com.autentia.tutorial.springhateoas.soccer.api.controller;

import com.autentia.tutorial.springhateoas.soccer.api.exception.InvalidInputDataException;
import com.autentia.tutorial.springhateoas.soccer.api.exception.ResourceNotFoundException;
import com.autentia.tutorial.springhateoas.soccer.dao.StadiumDao;
import com.autentia.tutorial.springhateoas.soccer.model.Stadium;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@Controller
public class StadiumController {

    private static final Logger LOG = LoggerFactory.getLogger(StadiumController.class);

    private final StadiumDao stadidumDao;

    @Autowired
    public StadiumController(StadiumDao stadidumDao) {
        this.stadidumDao = stadidumDao;
    }

    @RequestMapping(value = "/teams/{teamId}/stadium", method = RequestMethod.GET)
    public @ResponseBody Stadium getByTeamId(@PathVariable int teamId) {
        LOG.trace("Recibida solicitud para devolver el estadio del equipo con id {}", teamId);
        final Stadium stadium = stadidumDao.getByTeamId(teamId);
        if (stadium == null) {
            LOG.info("El estadio con del equipo con id {} no existe", teamId);
            throw new ResourceNotFoundException();
        }
        addStadiumLinks(stadium);
        return stadium;
    }


    @RequestMapping(value = "/teams/{teamId}/stadium", method = RequestMethod.POST)
    public @ResponseBody HttpEntity<Stadium> create(@PathVariable int teamId, @RequestBody Stadium stadium) {

        stadium.setTeamId(teamId);
        LOG.trace("Recibida solicitud para crear el estadio {}", stadium);
        validateNewStadium(stadium);

        try {
            stadium.setStadiumId(stadidumDao.persist(stadium));
        } catch (IllegalArgumentException iae) {
            LOG.error("No se puede crear el estadio {}", stadium, iae);
            throw new InvalidInputDataException("Los datos del estadio son erroneos");
        }

        addStadiumLinks(stadium);
        final HttpHeaders headers = createHeadersWithResourceLocation(stadium.getTeamId());

        return new ResponseEntity<>(stadium, headers, HttpStatus.CREATED);
    }

    private void addStadiumLinks(Stadium stadium) {
        addSelfLink(stadium);
        addTeamLink(stadium);
    }

    private void addSelfLink(Stadium stadium) {
        stadium.add(linkTo(methodOn(StadiumController.class).getByTeamId(stadium.getTeamId())).withSelfRel());
    }

    private void addTeamLink(Stadium stadium) {
        stadium.add(linkTo(methodOn(TeamController.class).getById(stadium.getTeamId())).withRel("team"));
    }

    private void validateNewStadium(Stadium stadium) {
        validateCapacity(stadium.getCapacity());
        validateName(stadium.getName());
        validateCity(stadium.getCity());
    }

    private void validateCapacity(int capacity) {
        if (capacity <= 0) {
            final String errorMsg = "La capacidad tiene que ser mayor que 0";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateName(String name) {
        if (StringUtils.isBlank(name)) {
            final String errorMsg = "El nombre del estadio es requerido";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private void validateCity(String city) {
        if (StringUtils.isBlank(city)) {
            final String errorMsg = "La ciudad del estadio es requerida";
            LOG.info(errorMsg);
            throw new InvalidInputDataException(errorMsg);
        }
    }

    private HttpHeaders createHeadersWithResourceLocation(int teamId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(StadiumController.class).getByTeamId(teamId)).toUri());
        return headers;
    }

}
