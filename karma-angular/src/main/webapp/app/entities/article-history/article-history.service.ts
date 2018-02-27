import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ArticleHistory } from './article-history.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ArticleHistory>;

@Injectable()
export class ArticleHistoryService {

    private resourceUrl =  SERVER_API_URL + 'api/article-histories';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(articleHistory: ArticleHistory): Observable<EntityResponseType> {
        const copy = this.convert(articleHistory);
        return this.http.post<ArticleHistory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(articleHistory: ArticleHistory): Observable<EntityResponseType> {
        const copy = this.convert(articleHistory);
        return this.http.put<ArticleHistory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ArticleHistory>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ArticleHistory[]>> {
        const options = createRequestOption(req);
        return this.http.get<ArticleHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ArticleHistory[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ArticleHistory = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ArticleHistory[]>): HttpResponse<ArticleHistory[]> {
        const jsonResponse: ArticleHistory[] = res.body;
        const body: ArticleHistory[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ArticleHistory.
     */
    private convertItemFromServer(articleHistory: ArticleHistory): ArticleHistory {
        const copy: ArticleHistory = Object.assign({}, articleHistory);
        copy.dateAccessed = this.dateUtils
            .convertDateTimeFromServer(articleHistory.dateAccessed);
        return copy;
    }

    /**
     * Convert a ArticleHistory to a JSON which can be sent to the server.
     */
    private convert(articleHistory: ArticleHistory): ArticleHistory {
        const copy: ArticleHistory = Object.assign({}, articleHistory);

        copy.dateAccessed = this.dateUtils.toDate(articleHistory.dateAccessed);
        return copy;
    }
}
