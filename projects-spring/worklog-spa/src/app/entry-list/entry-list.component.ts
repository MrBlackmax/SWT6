import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-entry-list',
  templateUrl: './entry-list.component.html',
  styleUrls: ['./entry-list.component.css']
})
export class EntryListComponent implements OnInit {

  errorInfo: string | null = null;

  constructor() {
  }

  ngOnInit(): void {
  }

  refreshEntryList(emplId: number): void {
  }

  private displayError(resp: HttpErrorResponse): void {
    this.errorInfo = `${resp.error.error} (${resp.error.status}): ${resp.error.message}.`;
  }

  private resetError(): void {
    this.errorInfo = null;
  }
}
