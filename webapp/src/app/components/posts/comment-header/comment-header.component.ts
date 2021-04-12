import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Comment } from '../../../models/content';
import { User } from '../../../models/user';

@Component({
  selector: 'app-comment-header',
  templateUrl: './comment-header.component.html',
  styleUrls: ['./comment-header.component.css']
})
export class CommentHeaderComponent implements OnInit {

  @Input()
  comment!: Comment;

  postedBy!: User | undefined;
  postedTo!: User | undefined;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.postedBy = this.userService.users.has(this.comment.postedByAlias) ? this.userService.users.get(this.comment.postedByAlias) : undefined;
    this.postedTo = this.comment.postedToAlias && this.userService.users.has(this.comment.postedToAlias) ? this.userService.users.get(this.comment.postedToAlias) : undefined;
  }

}
