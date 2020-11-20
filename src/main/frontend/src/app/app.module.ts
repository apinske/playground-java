import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { Configuration } from "build/playground-api/configuration";
import { PlaygroundApiModule } from 'build/playground-api/api.module';
import { CamundaApiModule } from 'build/camunda-api/api.module';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
    }), BrowserAnimationsModule,
    MatToolbarModule, MatIconModule, MatButtonModule, MatListModule, MatFormFieldModule, MatInputModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
