import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Aviso } from 'app/shared/model/aviso.model';
import { AvisoService } from './aviso.service';
import { AvisoComponent } from './aviso.component';
import { AvisoDetailComponent } from './aviso-detail.component';
import { AvisoUpdateComponent } from './aviso-update.component';
import { AvisoDeletePopupComponent } from './aviso-delete-dialog.component';
import { IAviso } from 'app/shared/model/aviso.model';

@Injectable({ providedIn: 'root' })
export class AvisoResolve implements Resolve<IAviso> {
    constructor(private service: AvisoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAviso> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Aviso>) => response.ok),
                map((aviso: HttpResponse<Aviso>) => aviso.body)
            );
        }
        return of(new Aviso());
    }
}

export const avisoRoute: Routes = [
    {
        path: '',
        component: AvisoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avisos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AvisoDetailComponent,
        resolve: {
            aviso: AvisoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avisos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AvisoUpdateComponent,
        resolve: {
            aviso: AvisoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avisos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AvisoUpdateComponent,
        resolve: {
            aviso: AvisoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avisos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const avisoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AvisoDeletePopupComponent,
        resolve: {
            aviso: AvisoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Avisos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
