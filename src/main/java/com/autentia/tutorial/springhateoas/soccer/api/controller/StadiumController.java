package com.autentia.tutorial.springhateoas.soccer.api.controller;

import com.autentia.tutorial.springhateoas.soccer.api.exception.InvalidInputDataException;
import com.autentia.tutorial.springhateoas.soccer.api.exception.ResourceNotFoundException;
import com.autentia.tutorial.springhateoas.soccer.dao.StadiumDao;
import com.autentia.tutorial.springhateoas.soccer.model.Stadium;
import com.autentia.tutorial.springhateoas.soccer.model.StadiumShortInfo;
import com.autentia.tutorial.springhateoas.soccer.model.Stadiums;
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
@RequestMapping(value = "/stadiums")
public class StadiumController {

    private static final Logger LOG = LoggerFactory.getLogger(StadiumController.class);

    private final StadiumDao stadidumDao;

    @Autowired
    public StadiumController(StadiumDao stadidumDao) {
        this.stadidumDao = stadidumDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Stadiums getAll() {
        LOG.trace("Recibida solicitud para devolver todos los estadios");
        final List<Stadium> stadiums = stadidumDao.getAll();
        addTeamLink(stadiums);
        return new Stadiums(stadiums);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Stadium getById(@PathVariable int id) {
        LOG.trace("Recibida solicitud para devolver el estadio con id {}", id);
        final Stadium stadium = stadidumDao.getById(id);
        if (stadium == null) {
            LOG.info("El estadio con id {} no existe", id);
            throw new ResourceNotFoundException();
        }
        addTeamLink(stadium);
        return stadium;
    }


    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody HttpEntity<StadiumShortInfo> create(@RequestBody Stadium stadium) {

        LOG.trace("Recibida solicitud para crear el estadio {}", stadium);
        validateNewStadium(stadium);

        int newStadiumId = stadidumDao.persist(stadium);

        final StadiumShortInfo stadiumShortInfo = new StadiumShortInfo(stadium.getName());
        stadiumShortInfo.add(linkTo(methodOn(StadiumController.class).getById(newStadiumId)).withSelfRel());

        final HttpHeaders headers = createHeadersWithResourceLocation(newStadiumId);

        return new ResponseEntity<>(stadiumShortInfo, headers, HttpStatus.CREATED);
    }
    private void addTeamLink(List<Stadium> stadiums) {
        if (!CollectionUtils.isEmpty(stadiums)) {
            for (Stadium stadium : stadiums) {
                addTeamLink(stadium);
            }
        }
    }

    private void addTeamLink(Stadium stadium) {
        final TeamShortInfo team = stadium.getTeam();
        if (team != null) {
            team.add(linkTo(methodOn(TeamController.class).getById(team.getIdTeam())).withSelfRel());
        }
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

    private HttpHeaders createHeadersWithResourceLocation(int stadiumId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(StadiumController.class).getById(stadiumId)).toUri());
        return headers;
    }

}
