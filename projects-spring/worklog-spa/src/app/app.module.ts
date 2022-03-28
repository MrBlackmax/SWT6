import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import {EmployeeListComponent} from "./employee-list/employee-list.component";
import {EntryListComponent} from "./entry-list/entry-list.component";

@NgModule({
  declarations: [
    AppComponent,
    EmployeeListComponent,
    EntryListComponent
  ],
  imports: [
    BrowserModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
