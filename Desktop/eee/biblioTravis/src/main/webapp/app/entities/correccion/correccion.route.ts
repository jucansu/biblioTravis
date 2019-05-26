import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Correccion } from 'app/shared/model/correccion.model';
import { CorreccionService } from './correccion.service';
import { CorreccionComponent } from './correccion.component';
import { CorreccionDetailComponent } from './correccion-detail.component';
import { CorreccionUpdateComponent } from './correccion-update.component';
import { CorreccionDeletePopupComponent } from './correccion-delete-dialog.component';
import { ICorreccion } from 'app/shared/model/correccion.model';

@Injectable({ providedIn: 'root' })
export class CorreccionResolve implements Resolve<ICorreccion> {
    constructor(private service: CorreccionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICorreccion> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Correccion>) => response.ok),
                map((correccion: HttpResponse<Correccion>) => correccion.body)
            );
        }
        return of(new Correccion());
    }
}

export const correccionRoute: Routes = [
    {
        path: '',
        component: CorreccionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Correccions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CorreccionDetailComponent,
        resolve: {
            correccion: CorreccionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Correccions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CorreccionUpdateComponent,
        resolve: {
            correccion: CorreccionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Correccions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CorreccionUpdateComponent,
        resolve: {
            correccion: CorreccionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Correccions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const correccionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CorreccionDeletePopupComponent,
        resolve: {
            correccion: CorreccionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Correccions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
