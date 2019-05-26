import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAviso } from 'app/shared/model/aviso.model';

type EntityResponseType = HttpResponse<IAviso>;
type EntityArrayResponseType = HttpResponse<IAviso[]>;

@Injectable({ providedIn: 'root' })
export class AvisoService {
    public resourceUrl = SERVER_API_URL + 'api/avisos';

    constructor(protected http: HttpClient) {}

    create(aviso: IAviso): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(aviso);
        return this.http
            .post<IAviso>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(aviso: IAviso): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(aviso);
        return this.http
            .put<IAviso>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAviso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAviso[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(aviso: IAviso): IAviso {
        const copy: IAviso = Object.assign({}, aviso, {
            fecha: aviso.fecha != null && aviso.fecha.isValid() ? aviso.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((aviso: IAviso) => {
                aviso.fecha = aviso.fecha != null ? moment(aviso.fecha) : null;
            });
        }
        return res;
    }
}
