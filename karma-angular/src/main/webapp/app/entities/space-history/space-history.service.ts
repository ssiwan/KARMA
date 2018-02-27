import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SpaceHistory } from './space-history.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SpaceHistory>;

@Injectable()
export class SpaceHistoryService {

    private resourceUrl =  SERVER_API_URL + 'api/space-histories';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(spaceHistory: SpaceHistory): Observable<EntityResponseType> {
        const copy = this.convert(spaceHistory);
        return this.http.post<SpaceHistory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(spaceHistory: SpaceHistory): Observable<EntityResponseType> {
        const copy = this.convert(spaceHistory);
        return this.http.put<SpaceHistory>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SpaceHistory>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SpaceHistory[]>> {
        const options = createRequestOption(req);
        return this.http.get<SpaceHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SpaceHistory[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SpaceHistory = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SpaceHistory[]>): HttpResponse<SpaceHistory[]> {
        const jsonResponse: SpaceHistory[] = res.body;
        const body: SpaceHistory[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SpaceHistory.
     */
    private convertItemFromServer(spaceHistory: SpaceHistory): SpaceHistory {
        const copy: SpaceHistory = Object.assign({}, spaceHistory);
        copy.dateAccessed = this.dateUtils
            .convertDateTimeFromServer(spaceHistory.dateAccessed);
        return copy;
    }

    /**
     * Convert a SpaceHistory to a JSON which can be sent to the server.
     */
    private convert(spaceHistory: SpaceHistory): SpaceHistory {
        const copy: SpaceHistory = Object.assign({}, spaceHistory);

        copy.dateAccessed = this.dateUtils.toDate(spaceHistory.dateAccessed);
        return copy;
    }
}
