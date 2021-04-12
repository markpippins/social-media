import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Forum } from '../models/forum';

@Injectable({
  providedIn: 'root'
})
export class ForumService {

  FORUMS: string = 'http://localhost:8080/api/forums';

  constructor(private http: HttpClient) { }

  getForums() {
    return this.http.get<Forum[]>(this.FORUMS.concat('/all'));
  }

  getForumById(id: number) {
    return this.http.get<Forum>(this.FORUMS.concat('/id/' + id));
  }

}
