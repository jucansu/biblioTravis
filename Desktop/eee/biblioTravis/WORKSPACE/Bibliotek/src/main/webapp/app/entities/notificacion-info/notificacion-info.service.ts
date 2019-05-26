import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INotificacionInfo } from 'app/shared/model/notificacion-info.model';

type EntityResponseType = HttpResponse<INotificacionInfo>;
type EntityArrayResponseType = HttpResponse<INotificacionInfo[]>;

@Injectable({ providedIn: 'root' })
export class NotificacionInfoService {
    public resourceUrl = SERVER_API_URL + 'api/notificacion-infos';

    constructor(protected http: HttpClient) {}

    create(notificacionInfo: INotificacionInfo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(notificacionInfo);
        return this.http
            .post<INotificacionInfo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(notificacionInfo: INotificacionInfo): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(notificacionInfo);
        return this.http
            .put<INotificacionInfo>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<INotificacionInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<INotificacionInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(notificacionInfo: INotificacionInfo): INotificacionInfo {
        const copy: INotificacionInfo = Object.assign({}, notificacionInfo, {
            fechaInicio:
                notificacionInfo.fechaInicio != null && notificacionInfo.fechaInicio.isValid()
                    ? notificacionInfo.fechaInicio.format(DATE_FORMAT)
                    : null,
            fechaFin:
                notificacionInfo.fechaFin != null && notificacionInfo.fechaFin.isValid()
                    ? notificacionInfo.fechaFin.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaInicio = res.body.fechaInicio != null ? moment(res.body.fechaInicio) : null;
            res.body.fechaFin = res.body.fechaFin != null ? moment(res.body.fechaFin) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((notificacionInfo: INotificacionInfo) => {
                notificacionInfo.fechaInicio = notificacionInfo.fechaInicio != null ? moment(notificacionInfo.fechaInicio) : null;
                notificacionInfo.fechaFin = notificacionInfo.fechaFin != null ? moment(notificacionInfo.fechaFin) : null;
            });
        }
        return res;
    }
}
