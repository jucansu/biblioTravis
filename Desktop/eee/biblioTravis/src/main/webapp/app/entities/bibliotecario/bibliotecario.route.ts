import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bibliotecario } from 'app/shared/model/bibliotecario.model';
import { BibliotecarioService } from './bibliotecario.service';
import { BibliotecarioComponent } from './bibliotecario.component';
import { BibliotecarioDetailComponent } from './bibliotecario-detail.component';
import { BibliotecarioUpdateComponent } from './bibliotecario-update.component';
import { BibliotecarioDeletePopupComponent } from './bibliotecario-delete-dialog.component';
import { IBibliotecario } from 'app/shared/model/bibliotecario.model';

@Injectable({ providedIn: 'root' })
export class BibliotecarioResolve implements Resolve<IBibliotecario> {
    constructor(private service: BibliotecarioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBibliotecario> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Bibliotecario>) => response.ok),
                map((bibliotecario: HttpResponse<Bibliotecario>) => bibliotecario.body)
            );
        }
        return of(new Bibliotecario());
    }
}

export const bibliotecarioRoute: Routes = [
    {
        path: '',
        component: BibliotecarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BibliotecarioDetailComponent,
        resolve: {
            bibliotecario: BibliotecarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BibliotecarioUpdateComponent,
        resolve: {
            bibliotecario: BibliotecarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecarios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BibliotecarioUpdateComponent,
        resolve: {
            bibliotecario: BibliotecarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bibliotecarioPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BibliotecarioDeletePopupComponent,
        resolve: {
            bibliotecario: BibliotecarioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
