import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICorreccion } from 'app/shared/model/correccion.model';

type EntityResponseType = HttpResponse<ICorreccion>;
type EntityArrayResponseType = HttpResponse<ICorreccion[]>;

@Injectable({ providedIn: 'root' })
export class CorreccionService {
    public resourceUrl = SERVER_API_URL + 'api/correccions';

    constructor(protected http: HttpClient) {}

    create(correccion: ICorreccion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(correccion);
        return this.http
            .post<ICorreccion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(correccion: ICorreccion): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(correccion);
        return this.http
            .put<ICorreccion>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICorreccion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICorreccion[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(correccion: ICorreccion): ICorreccion {
        const copy: ICorreccion = Object.assign({}, correccion, {
            fecha: correccion.fecha != null && correccion.fecha.isValid() ? correccion.fecha.format(DATE_FORMAT) : null
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
            res.body.forEach((correccion: ICorreccion) => {
                correccion.fecha = correccion.fecha != null ? moment(correccion.fecha) : null;
            });
        }
        return res;
    }
}
