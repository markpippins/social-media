import { Forum } from '../../../models/forum';
import { ForumService } from './../../../services/forum.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forum-list',
  templateUrl: './forum-list.component.html',
  styleUrls: ['./forum-list.component.css']
})
export class ForumListComponent implements OnInit {

  forums: Forum[] = [];

  constructor(private forumService: ForumService) { console.log('ForumService is constructed'); }

  ngOnDestroy(): void {

  }

  ngOnInit(): void {
    this.initializeData();
  }

  initializeData() {
    this.forumService.getForums().subscribe(data => {
      this.forums = data;
    })
  }
}
