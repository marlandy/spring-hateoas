package com.autentia.tutorial.springhateoas.soccer.dao;

import com.autentia.tutorial.springhateoas.soccer.model.Stadium;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StadiumDaoTest {

    @Autowired
    private StadiumDao stadiumDao;

    @Test
    public void shouldReturnAllStadiums() {
        final List<Stadium> stadiums = stadiumDao.getAll();

        assertNotNull(stadiums);
        assertEquals(3, stadiums.size());
    }

    @Test
    public void shouldReturnAStadiumById() {
        final List<Stadium> stadiums = stadiumDao.getAll();

        assertNotNull(stadiums);
        assertFalse(stadiums.isEmpty());

        final Stadium stadium = stadiums.get(0);
        final Stadium stadiumById = stadiumDao.getById(stadium.getStadiumId());

        assertNotNull(stadiumById);
        assertEquals(stadium.getId(), stadiumById.getId());
        assertEquals(stadium.getCapacity(), stadiumById.getCapacity());
        assertEquals(stadium.getCity(), stadiumById.getCity());
        assertEquals(stadium.getName(), stadiumById.getName());

    }

    @Test
    public void shouldReturnNullIfStadiumDoesntExists() {
        int unexistingStadiumId = 999;
        assertNull(stadiumDao.getById(unexistingStadiumId));
    }

    @Test
    public void shouldReturnAStadiumByTeamId() {

        int realMadridTeamId = 5000;

        final Stadium stadiumByTeamId = stadiumDao.getByTeamId(realMadridTeamId);

        assertNotNull(stadiumByTeamId);
        assertEquals(1000, stadiumByTeamId.getStadiumId());
        assertEquals(85454, stadiumByTeamId.getCapacity());
        assertEquals("Madrid", stadiumByTeamId.getCity());
        assertEquals("Santiago Bernabeu", stadiumByTeamId.getName());
        assertEquals(realMadridTeamId, stadiumByTeamId.getTeamId());

    }

    @Test
    public void shouldReturnNullIfThereIsNotStadiumWithTheRequestedTeamId() {
        int unexistingTeamId = 999;
        assertNull(stadiumDao.getByTeamId(unexistingTeamId));
    }

    @Test
    public void shouldPersistAStadium() {
        final Stadium stadium = new Stadium();
        stadium.setCapacity(28963);
        stadium.setCity("Málaga");
        stadium.setName("La Rosaleda");
        stadium.setTeamId(5003);

        int stadiumID = stadiumDao.persist(stadium);
        final Stadium stadiumFromDB = stadiumDao.getById(stadiumID);

        assertNotNull(stadiumFromDB);
        assertEquals(stadium.getCapacity(), stadiumFromDB.getCapacity());
        assertEquals(stadium.getCity(), stadiumFromDB.getCity());
        assertEquals(stadium.getName(), stadiumFromDB.getName());

        deleteStadiumAndValidateDeletion(stadiumFromDB);
    }

    @Test
    public void shouldFailIfTeamIdNotExists() {

        int unexistingTeamId = 999;
        final Stadium stadium = new Stadium();
        stadium.setCapacity(28963);
        stadium.setCity("Málaga");
        stadium.setName("La Rosaleda");
        stadium.setTeamId(unexistingTeamId);
        try {
            stadiumDao.persist(stadium);
            fail("El test debió fallar porque el id del equipo no existe");
        } catch (IllegalArgumentException iae) {
            assertEquals("No existe un equipo con id " + unexistingTeamId, iae.getMessage());
        }
    }

    private void deleteStadiumAndValidateDeletion(Stadium stadium) {
        stadiumDao.delete(stadium.getStadiumId());
        assertNull(stadiumDao.getById(stadium.getStadiumId()));
    }


}
