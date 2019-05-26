import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SalaEstudio } from 'app/shared/model/sala-estudio.model';
import { SalaEstudioService } from './sala-estudio.service';
import { SalaEstudioComponent } from './sala-estudio.component';
import { SalaEstudioDetailComponent } from './sala-estudio-detail.component';
import { SalaEstudioUpdateComponent } from './sala-estudio-update.component';
import { SalaEstudioDeletePopupComponent } from './sala-estudio-delete-dialog.component';
import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';

@Injectable({ providedIn: 'root' })
export class SalaEstudioResolve implements Resolve<ISalaEstudio> {
    constructor(private service: SalaEstudioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalaEstudio> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SalaEstudio>) => response.ok),
                map((salaEstudio: HttpResponse<SalaEstudio>) => salaEstudio.body)
            );
        }
        return of(new SalaEstudio());
    }
}

export const salaEstudioRoute: Routes = [
    {
        path: '',
        component: SalaEstudioComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SalaEstudios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalaEstudioDetailComponent,
        resolve: {
            salaEstudio: SalaEstudioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaEstudios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalaEstudioUpdateComponent,
        resolve: {
            salaEstudio: SalaEstudioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaEstudios'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalaEstudioUpdateComponent,
        resolve: {
            salaEstudio: SalaEstudioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaEstudios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salaEstudioPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalaEstudioDeletePopupComponent,
        resolve: {
            salaEstudio: SalaEstudioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaEstudios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
