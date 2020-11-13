import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';

import { ThingService } from 'build/openapi/api/thing.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'playground-angular';

  $sites = [
    { name: 'Data', link: '/playground-data' },
    { name: 'API', link: '/webjars/swagger-ui/index.html?url=/api.yaml' },
    { name: 'Camunda', link: '/camunda/app/cockpit/' },
    { name: 'Camunda API', link: '/camunda/rest/engine' },
    { name: 'DB', link: '/h2-console' }
  ];

  newThingName = new FormControl('');

  $things = this.thingService.getThings();
  $tasks = [];

  constructor(private readonly thingService: ThingService) { }

  public createNewThing() {
    this.thingService.createThing({ id: 0, name: this.newThingName.value }).subscribe(() => {
      this.$things = this.thingService.getThings();
    });
    this.newThingName.setValue('');
  }
}
