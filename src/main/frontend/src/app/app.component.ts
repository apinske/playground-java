import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';

import { ThingService } from 'build/playground-api/api/thing.service';
import { TaskService } from 'build/camunda-api/api/task.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'playground-angular';

  $sites = [
    { name: 'API', link: '/webjars/swagger-ui/index.html?url=/api.yaml' },
    { name: 'Camunda', link: '/camunda/app/cockpit/' },
    { name: 'Camunda API', link: '/engine-rest/incident' },
    { name: 'DB', link: '/h2-console' }
  ];

  newThingName = new FormControl('');

  $things = this.thingService.getThings();
  $tasks = this.taskService.getTasks();

  constructor(private readonly thingService: ThingService, private readonly taskService: TaskService) { }

  public createNewThing() {
    this.thingService.createThing({ id: 0, name: this.newThingName.value }).subscribe(() => {
      this.$things = this.thingService.getThings();
    });
    this.newThingName.setValue('');
  }

  public deleteThing(id: number) {
    this.thingService.deleteThing(id).subscribe(() => {
      this.$things = this.thingService.getThings();
    });
  }
}
