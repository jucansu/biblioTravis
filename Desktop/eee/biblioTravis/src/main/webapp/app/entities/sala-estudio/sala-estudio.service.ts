import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';

type EntityResponseType = HttpResponse<ISalaEstudio>;
type EntityArrayResponseType = HttpResponse<ISalaEstudio[]>;

@Injectable({ providedIn: 'root' })
export class SalaEstudioService {
    public resourceUrl = SERVER_API_URL + 'api/sala-estudios';

    constructor(protected http: HttpClient) {}

    create(salaEstudio: ISalaEstudio): Observable<EntityResponseType> {
        return this.http.post<ISalaEstudio>(this.resourceUrl, salaEstudio, { observe: 'response' });
    }

    update(salaEstudio: ISalaEstudio): Observable<EntityResponseType> {
        return this.http.put<ISalaEstudio>(this.resourceUrl, salaEstudio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISalaEstudio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISalaEstudio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
