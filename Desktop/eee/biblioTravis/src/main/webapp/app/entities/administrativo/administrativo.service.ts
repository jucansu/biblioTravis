import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdministrativo } from 'app/shared/model/administrativo.model';

type EntityResponseType = HttpResponse<IAdministrativo>;
type EntityArrayResponseType = HttpResponse<IAdministrativo[]>;

@Injectable({ providedIn: 'root' })
export class AdministrativoService {
    public resourceUrl = SERVER_API_URL + 'api/administrativos';

    constructor(protected http: HttpClient) {}

    create(administrativo: IAdministrativo): Observable<EntityResponseType> {
        return this.http.post<IAdministrativo>(this.resourceUrl, administrativo, { observe: 'response' });
    }

    update(administrativo: IAdministrativo): Observable<EntityResponseType> {
        return this.http.put<IAdministrativo>(this.resourceUrl, administrativo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdministrativo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdministrativo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
