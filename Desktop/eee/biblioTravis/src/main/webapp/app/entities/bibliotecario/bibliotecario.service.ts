import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBibliotecario } from 'app/shared/model/bibliotecario.model';

type EntityResponseType = HttpResponse<IBibliotecario>;
type EntityArrayResponseType = HttpResponse<IBibliotecario[]>;

@Injectable({ providedIn: 'root' })
export class BibliotecarioService {
    public resourceUrl = SERVER_API_URL + 'api/bibliotecarios';

    constructor(protected http: HttpClient) {}

    create(bibliotecario: IBibliotecario): Observable<EntityResponseType> {
        return this.http.post<IBibliotecario>(this.resourceUrl, bibliotecario, { observe: 'response' });
    }

    update(bibliotecario: IBibliotecario): Observable<EntityResponseType> {
        return this.http.put<IBibliotecario>(this.resourceUrl, bibliotecario, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBibliotecario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBibliotecario[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
