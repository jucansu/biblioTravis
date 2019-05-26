import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';

type EntityResponseType = HttpResponse<IBiblioteca>;
type EntityArrayResponseType = HttpResponse<IBiblioteca[]>;

@Injectable({ providedIn: 'root' })
export class BibliotecaService {
    public resourceUrl = SERVER_API_URL + 'api/bibliotecas';

    constructor(protected http: HttpClient) {}

    create(biblioteca: IBiblioteca): Observable<EntityResponseType> {
        return this.http.post<IBiblioteca>(this.resourceUrl, biblioteca, { observe: 'response' });
    }

    update(biblioteca: IBiblioteca): Observable<EntityResponseType> {
        return this.http.put<IBiblioteca>(this.resourceUrl, biblioteca, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBiblioteca>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBiblioteca[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    traeBiblioteca(): Observable<IBiblioteca[]> {
        var url: string = 'api/bibliotecas/all';
        console.log(this.http.get<IBiblioteca[]>(url));
        return this.http.get<IBiblioteca[]>(url);
    }

    traeBibliotecaOrden(): Observable<IBiblioteca[]> {
        var url: string = 'api/bibliotecas/all/order';
        console.log(this.http.get<IBiblioteca[]>(url));
        return this.http.get<IBiblioteca[]>(url);
    }

    traeMisBibliotecas(id: number): Observable<IBiblioteca[]> {
        var url: string = 'api/bibliotecas/mias/' + id;
        console.log(this.http.get<IBiblioteca[]>(url));
        return this.http.get<IBiblioteca[]>(url);
    }
    puntuaPositivo(id: number): Observable<IBiblioteca[]> {
        var url: string = 'api/bibliotecas/positivo/' + id;
        console.log(this.http.get<IBiblioteca[]>(url));
        return this.http.get<IBiblioteca[]>(url);
    }

    puntuaNegativo(id: number): Observable<IBiblioteca[]> {
        var url: string = 'api/bibliotecas/negativo/' + id;
        console.log(this.http.get<IBiblioteca[]>(url));
        return this.http.get<IBiblioteca[]>(url);
    }
}
