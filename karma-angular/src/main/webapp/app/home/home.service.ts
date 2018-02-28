import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Space } from '../entities/space/space.model';
import { Tag } from '../entities/tag/tag.model';
import { createRequestOption } from '../shared';


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
  
  searchTitle(searchString: string): Observable<HttpResponse<Article>> {
    return this.articleService.search(searchString);
  }


}
