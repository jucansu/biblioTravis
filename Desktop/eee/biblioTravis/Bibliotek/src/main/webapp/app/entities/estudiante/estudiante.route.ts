import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Estudiante } from 'app/shared/model/estudiante.model';
import { EstudianteService } from './estudiante.service';
import { EstudianteComponent } from './estudiante.component';
import { EstudianteDetailComponent } from './estudiante-detail.component';
import { EstudianteUpdateComponent } from './estudiante-update.component';
import { EstudianteDeletePopupComponent } from './estudiante-delete-dialog.component';
import { IEstudiante } from 'app/shared/model/estudiante.model';

@Injectable({ providedIn: 'root' })
export class EstudianteResolve implements Resolve<IEstudiante> {
    constructor(private service: EstudianteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstudiante> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Estudiante>) => response.ok),
                map((estudiante: HttpResponse<Estudiante>) => estudiante.body)
            );
        }
        return of(new Estudiante());
    }
}

export const estudianteRoute: Routes = [
    {
        path: '',
        component: EstudianteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estudiantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: EstudianteDetailComponent,
        resolve: {
            estudiante: EstudianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estudiantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: EstudianteUpdateComponent,
        resolve: {
            estudiante: EstudianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estudiantes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: EstudianteUpdateComponent,
        resolve: {
            estudiante: EstudianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estudiantes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estudiantePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: EstudianteDeletePopupComponent,
        resolve: {
            estudiante: EstudianteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Estudiantes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
