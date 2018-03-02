import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {JhiDateUtils} from 'ng-jhipster';

import {Article, ArticleService} from '../entities/article';


export type EntityResponseType = HttpResponse<Article>;

@Injectable()
export class HomeService {
  isSaving: boolean;
  articles: Article[];

  constructor(
    private http: HttpClient,
    private dateUtils: JhiDateUtils,
    private articleService: ArticleService
  ) {
  }


  searchTitle(searchString: string): Observable<HttpResponse<Article[]>> {
    return this.articleService.search(searchString);
  }

  findRecentlyAccessed(userId: number): Observable<HttpResponse<Article[]>> {
    return this.articleService.findRecentlyAccessed(userId);
  }


}
