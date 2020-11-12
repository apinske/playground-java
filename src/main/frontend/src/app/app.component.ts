import { Component } from '@angular/core';

import { ThingService } from 'build/openapi/api/thing.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'playground-angular';

  $things = this.thingService.getThings();

  constructor(private readonly thingService: ThingService) {}
}
