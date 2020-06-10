package eu.pinske.playground.process.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.pinske.playground.dao.ThingRepository;
import eu.pinske.playground.model.Thing;

@Component
public class TaskDelegate implements JavaDelegate {
	private static Logger log = LoggerFactory.getLogger(TaskDelegate.class);

	@Autowired
	private ThingRepository thingRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		if (!execution.hasVariable("thing")) {
			Thing thing = new Thing();
			thing.setName("prozess");
			thing = thingRepository.save(thing);
			execution.setVariable("thing", thing);
			log.info("created {}", thing);
		} else {
			log.info("accessing thing");
			Thing thing = (Thing) execution.getVariable("thing");
			log.info("found {}", thing);
			thing.setName("prozess_ende");
		}
	}
}
