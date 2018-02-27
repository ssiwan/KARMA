import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { TagHistory } from './tag-history.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TagHistory>;

@Injectable()
export class TagHistoryService {

    private resourceUrl =  SERVER_API_URL + 'api/tag-histories';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(tagHistory: TagHistory): Observable<EntityResponseType> {
        const copy = this.convert(tagHistory);
        return this.http.post<TagHistory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tagHistory: TagHistory): Observable<EntityResponseType> {
        const copy = this.convert(tagHistory);
        return this.http.put<TagHistory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TagHistory>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TagHistory[]>> {
        const options = createRequestOption(req);
        return this.http.get<TagHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TagHistory[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TagHistory = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TagHistory[]>): HttpResponse<TagHistory[]> {
        const jsonResponse: TagHistory[] = res.body;
        const body: TagHistory[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TagHistory.
     */
    private convertItemFromServer(tagHistory: TagHistory): TagHistory {
        const copy: TagHistory = Object.assign({}, tagHistory);
        copy.dateAccessed = this.dateUtils
            .convertDateTimeFromServer(tagHistory.dateAccessed);
        return copy;
    }

    /**
     * Convert a TagHistory to a JSON which can be sent to the server.
     */
    private convert(tagHistory: TagHistory): TagHistory {
        const copy: TagHistory = Object.assign({}, tagHistory);

        copy.dateAccessed = this.dateUtils.toDate(tagHistory.dateAccessed);
        return copy;
    }
}
