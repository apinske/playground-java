package eu.pinske.playground.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import eu.pinske.playground.model.Thing;

@RepositoryRestResource
public interface ThingRepository extends JpaRepository<Thing, Long> {

	List<Thing> findByName(String name);
}
