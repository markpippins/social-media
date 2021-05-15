import { AppConfigService } from './app-config.service';
import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Forum } from '../models/forum';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class ForumService {

  FORUMS!: string;

  constructor(private http: HttpClient, private appConfigService: AppConfigService) {
    this.FORUMS = this.appConfigService.host + '/api/forums';
  }

  getForums() {
    return this.http.get<Forum[]>(this.FORUMS.concat('/all'));
  }

  getForumById(id: number) {
    return this.http.get<Forum>(this.FORUMS.concat('/id/' + id));
  }

}
