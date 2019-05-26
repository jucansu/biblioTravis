import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';

import { IEstudiante } from '../../shared/model/estudiante.model';

type EntityResponseType = HttpResponse<IEstudiante>;
type EntityArrayResponseType = HttpResponse<IEstudiante[]>;

@Injectable({ providedIn: 'root' })
export class EstudianteService {
    public resourceUrl = SERVER_API_URL + 'api/estudiantes';

    constructor(protected http: HttpClient) {}

    create(estudiante: IEstudiante): Observable<EntityResponseType> {
        return this.http.post<IEstudiante>(this.resourceUrl, estudiante, { observe: 'response' });
    }

    update(estudiante: IEstudiante): Observable<EntityResponseType> {
        return this.http.put<IEstudiante>(this.resourceUrl, estudiante, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEstudiante>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEstudiante[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
    traeEstudiante(id: number): Observable<IEstudiante> {
        var url: string = 'api/estudiante/logueado/' + id;

        return this.http.get<IEstudiante>(url);
    }
    pausa(id: number): Observable<IEstudiante> {
        var url: string = 'api/estudiante/pausa/' + id;
        return this.http.get<IEstudiante>(url);
    }
}
