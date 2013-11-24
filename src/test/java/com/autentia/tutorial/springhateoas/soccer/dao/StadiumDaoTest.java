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
        assertEquals(4, stadiums.size());
    }

    @Test
    public void shouldReturnAStadiumById() {
        final List<Stadium> stadiums = stadiumDao.getAll();

        assertNotNull(stadiums);
        assertFalse(stadiums.isEmpty());

        final Stadium stadium = stadiums.get(0);
        final Stadium stadiumById = stadiumDao.getById(stadium.getId());

        assertNotNull(stadiumById);
        assertEquals(stadium.getId(), stadiumById.getId());
        assertEquals(stadium.getCapacity(), stadiumById.getCapacity());
        assertEquals(stadium.getCity(), stadiumById.getCity());
        assertEquals(stadium.getName(), stadiumById.getName());
        assertNotNull(stadiumById.getTeam());
        assertEquals(stadium.getTeam(), stadiumById.getTeam());

    }

    @Test
    public void shouldReturnNullIfStadiumDoesntExists() {
        int unexistingStadiumId = 999;
        assertNull(stadiumDao.getById(unexistingStadiumId));
    }

    @Test
    public void shouldPersistAStadium() {
        final Stadium stadium = new Stadium();
        stadium.setCapacity(12345);
        stadium.setCity("Valencia");
        stadium.setName("Un estadio");

        int stadiumID = stadiumDao.persist(stadium);
        final Stadium stadiumFromDB = stadiumDao.getById(stadiumID);

        assertNotNull(stadiumFromDB);
        assertEquals(stadium.getCapacity(), stadiumFromDB.getCapacity());
        assertEquals(stadium.getCity(), stadiumFromDB.getCity());
        assertEquals(stadium.getName(), stadiumFromDB.getName());

        deleteStadiumAndValidateDeletion(stadiumFromDB);
    }

    private void deleteStadiumAndValidateDeletion(Stadium stadium) {
        stadiumDao.delete(stadium.getId());
        assertNull(stadiumDao.getById(stadium.getId()));
    }


}
