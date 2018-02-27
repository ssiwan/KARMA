import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ArticleType } from './article-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ArticleType>;

@Injectable()
export class ArticleTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/article-types';

    constructor(private http: HttpClient) { }

    create(articleType: ArticleType): Observable<EntityResponseType> {
        const copy = this.convert(articleType);
        return this.http.post<ArticleType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(articleType: ArticleType): Observable<EntityResponseType> {
        const copy = this.convert(articleType);
        return this.http.put<ArticleType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ArticleType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ArticleType[]>> {
        const options = createRequestOption(req);
        return this.http.get<ArticleType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ArticleType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ArticleType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ArticleType[]>): HttpResponse<ArticleType[]> {
        const jsonResponse: ArticleType[] = res.body;
        const body: ArticleType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ArticleType.
     */
    private convertItemFromServer(articleType: ArticleType): ArticleType {
        const copy: ArticleType = Object.assign({}, articleType);
        return copy;
    }

    /**
     * Convert a ArticleType to a JSON which can be sent to the server.
     */
    private convert(articleType: ArticleType): ArticleType {
        const copy: ArticleType = Object.assign({}, articleType);
        return copy;
    }
}
