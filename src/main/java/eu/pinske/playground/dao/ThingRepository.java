package eu.pinske.playground.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.pinske.playground.model.Thing;

public interface ThingRepository extends JpaRepository<Thing, Long> {

    List<Thing> findByName(String name);
}
