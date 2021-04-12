import { Reaction } from './reaction';

export interface Content {
  id: number;
  text: string;
  postedByAlias: string;
  postedToAlias: string;
  postedDate: string;
  updated: string;
  replies: Comment[];
  reactions: Reaction[];
}

export interface Post extends Content {
  forumId: number;
  title: string;
}

export interface Comment extends Content {
  post: Post;
  parent: Comment;
}
