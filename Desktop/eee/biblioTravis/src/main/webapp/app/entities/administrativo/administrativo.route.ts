import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Administrativo } from 'app/shared/model/administrativo.model';
import { AdministrativoService } from './administrativo.service';
import { AdministrativoComponent } from './administrativo.component';
import { AdministrativoDetailComponent } from './administrativo-detail.component';
import { AdministrativoUpdateComponent } from './administrativo-update.component';
import { AdministrativoDeletePopupComponent } from './administrativo-delete-dialog.component';
import { IAdministrativo } from 'app/shared/model/administrativo.model';

@Injectable({ providedIn: 'root' })
export class AdministrativoResolve implements Resolve<IAdministrativo> {
    constructor(private service: AdministrativoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAdministrativo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Administrativo>) => response.ok),
                map((administrativo: HttpResponse<Administrativo>) => administrativo.body)
            );
        }
        return of(new Administrativo());
    }
}

export const administrativoRoute: Routes = [
    {
        path: '',
        component: AdministrativoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Administrativos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AdministrativoDetailComponent,
        resolve: {
            administrativo: AdministrativoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Administrativos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AdministrativoUpdateComponent,
        resolve: {
            administrativo: AdministrativoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Administrativos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AdministrativoUpdateComponent,
        resolve: {
            administrativo: AdministrativoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Administrativos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const administrativoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AdministrativoDeletePopupComponent,
        resolve: {
            administrativo: AdministrativoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Administrativos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
