import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { Configuration } from "build/openapi/configuration";
import { ApiModule } from 'build/openapi/api.module';

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule, 
    HttpClientModule, 
    ApiModule.forRoot(() => {
      return new Configuration({ basePath: 'http://localhost:8080/playground-api' });
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
