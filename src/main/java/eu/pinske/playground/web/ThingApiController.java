package eu.pinske.playground.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.pinske.playground.dao.ThingRepository;
import eu.pinske.playground.model.Thing;
import eu.pinske.playground.web.api.ThingApi;
import eu.pinske.playground.web.api.model.ThingDto;

@Controller
@RequestMapping("/playground-api")
@Transactional
public class ThingApiController implements ThingApi {

	@Autowired
	private ThingRepository thingRepository;

	@Override
	public ResponseEntity<List<ThingDto>> getThings(String name) {
		if (name != null) {
			return ResponseEntity.ok(thingRepository.findByName(name).stream().map(this::map).collect(toList()));
		} else {
			return ResponseEntity.ok(thingRepository.findAll().stream().map(this::map).collect(toList()));
		}
	}

	@Override
	public ResponseEntity<ThingDto> getThing(Long id) {
		return thingRepository.findById(id).map(this::map).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Override
	public ResponseEntity<Void> createThing(ThingDto thing) {
		Thing t = new Thing();
		t.setName(thing.getName());
		thingRepository.save(t);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> changeThing(Long id, ThingDto thing) {
		thingRepository.findById(id).ifPresent(t -> {
			t.setName(thing.getName());
		});
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteThing(Long id) {
		thingRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	private ThingDto map(Thing t) {
		return new ThingDto().id(t.getId()).name(t.getName());
	}
}
