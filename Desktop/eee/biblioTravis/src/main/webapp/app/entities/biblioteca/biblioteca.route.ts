import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Biblioteca } from 'app/shared/model/biblioteca.model';
import { BibliotecaService } from './biblioteca.service';
import { BibliotecaComponent } from './biblioteca.component';
import { BibliotecaDetailComponent } from './biblioteca-detail.component';
import { BibliotecaUpdateComponent } from './biblioteca-update.component';
import { BibliotecaDeletePopupComponent } from './biblioteca-delete-dialog.component';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';

@Injectable({ providedIn: 'root' })
export class BibliotecaResolve implements Resolve<IBiblioteca> {
    constructor(private service: BibliotecaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBiblioteca> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Biblioteca>) => response.ok),
                map((biblioteca: HttpResponse<Biblioteca>) => biblioteca.body)
            );
        }
        return of(new Biblioteca());
    }
}

export const bibliotecaRoute: Routes = [
    {
        path: '',
        component: BibliotecaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Bibliotecas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BibliotecaDetailComponent,
        resolve: {
            biblioteca: BibliotecaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BibliotecaUpdateComponent,
        resolve: {
            biblioteca: BibliotecaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecas'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BibliotecaUpdateComponent,
        resolve: {
            biblioteca: BibliotecaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bibliotecaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BibliotecaDeletePopupComponent,
        resolve: {
            biblioteca: BibliotecaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bibliotecas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
