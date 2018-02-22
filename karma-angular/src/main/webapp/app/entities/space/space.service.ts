import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Space } from './space.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Space>;

@Injectable()
export class SpaceService {

    private resourceUrl =  SERVER_API_URL + 'api/spaces';

    constructor(private http: HttpClient) { }

    create(space: Space): Observable<EntityResponseType> {
        const copy = this.convert(space);
        return this.http.post<Space>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(space: Space): Observable<EntityResponseType> {
        const copy = this.convert(space);
        return this.http.put<Space>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Space>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Space[]>> {
        const options = createRequestOption(req);
        return this.http.get<Space[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Space[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Space = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Space[]>): HttpResponse<Space[]> {
        const jsonResponse: Space[] = res.body;
        const body: Space[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Space.
     */
    private convertItemFromServer(space: Space): Space {
        const copy: Space = Object.assign({}, space);
        return copy;
    }

    /**
     * Convert a Space to a JSON which can be sent to the server.
     */
    private convert(space: Space): Space {
        const copy: Space = Object.assign({}, space);
        return copy;
    }
}
