import { Forum } from '../../../../models/forum';
import { User } from '../../../../models/user';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-forum-members',
  templateUrl: './forum-members.component.html',
  styleUrls: ['./forum-members.component.css']
})
export class ForumMembersComponent implements OnInit {

  @Input()
  forum!: Forum;

  members!: User[];

  constructor() { }

  ngOnInit(): void {
    this.members = this.forum.members;
  }

}
