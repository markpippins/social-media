import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-user-dash',
  templateUrl: './user-dash.component.html',
  styleUrls: ['./user-dash.component.css']
})
export class UserDashComponent implements OnInit {

  @Input()
  pageUserName: string | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
