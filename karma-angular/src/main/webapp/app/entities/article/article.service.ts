import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Article } from './article.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Article>;

@Injectable()
export class ArticleService {

    private resourceUrl =  SERVER_API_URL + 'api/articles';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(article: Article): Observable<EntityResponseType> {
        const copy = this.convert(article);
        return this.http.post<Article>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(article: Article): Observable<EntityResponseType> {
        const copy = this.convert(article);
        return this.http.put<Article>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Article>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

   search(searchString: string): Observable<HttpResponse<Article[]>> {
        return this.http.get<Article[]>(`${this.resourceUrl}/searchTitles/${searchString}`, {observe: 'response'})
            .map((res: HttpResponse<Article[]>) => this.convertArrayResponse(res));
    }

   searchBySpace(spaceId: number): Observable<HttpResponse<Article[]>> {
       return this.http.get<Article[]>(`${this.resourceUrl}/searchSpace/${spaceId}`, { observe: 'response'})
       .map((res: HttpResponse<Article[]>) => this.convertArrayResponse(res));
   }

  countBySpace(spaceId: number): Observable<HttpResponse<any>> {
    return this.http.get<any>(`${this.resourceUrl}/countBySpace/${spaceId}`, { observe: 'response'});

  }

    findRecentlyAccessed(userId: number): Observable<HttpResponse<Article[]>> {
      return this.http.get<Article[]>(`${this.resourceUrl}/recentlyAccessed/${userId}`, { observe: 'response'})
            .map((res: HttpResponse<Article[]>) => this.convertArrayResponse(res));
    }

    searchByTagId(tagId: number): Observable<EntityResponseType> {
     return this.http.get<Article>(`${this.resourceUrl}/tag/${tagId}`, { observe: 'response'})
          .map((res: EntityResponseType) => this.convertResponse(res));
    }

   findFrequentlyAccessed(): Observable<HttpResponse<Article[]>> {
      return this.http.get<Article[]>(`${this.resourceUrl}/frequent/`, { observe: 'response'})
            .map((res: HttpResponse<Article[]>) => this.convertArrayResponse(res));
    }

   query(req?: any): Observable<HttpResponse<Article[]>> {
        const options = createRequestOption(req);
        return this.http.get<Article[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Article[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Article = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Article[]>): HttpResponse<Article[]> {
        const jsonResponse: Article[] = res.body;
        const body: Article[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Article.
     */
    private convertItemFromServer(article: Article): Article {
        const copy: Article = Object.assign({}, article);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(article.date);
        return copy;
    }

    /**
     * Convert a Article to a JSON which can be sent to the server.
     */
    private convert(article: Article): Article {
        const copy: Article = Object.assign({}, article);

        copy.date = this.dateUtils.toDate(article.date);
        return copy;
    }
}
