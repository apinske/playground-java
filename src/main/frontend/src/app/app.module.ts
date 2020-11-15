import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { Configuration } from "build/playground-api/configuration";
import { PlaygroundApiModule } from 'build/playground-api/api.module';
import { CamundaApiModule } from 'build/camunda-api/api.module';

import { AppComponent } from './app.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    PlaygroundApiModule.forRoot(() => {
      return new Configuration({ basePath: 'http://localhost:8080/playground-api' });
    }), CamundaApiModule.forRoot(() => {
      return new Configuration({ basePath: 'http://localhost:8080/engine-rest' });
    }), FontAwesomeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
