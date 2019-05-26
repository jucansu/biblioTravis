import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NotificacionInfo } from '../../shared/model/notificacion-info.model';
import { INotificacionInfo } from '../../shared/model/notificacion-info.model';
import { NotificacionInfoService } from './notificacion-info.service';
import { NotificacionInfoComponent } from './notificacion-info.component';

@Injectable({ providedIn: 'root' })
export class NotificacionInfoResolve implements Resolve<INotificacionInfo> {
    constructor(private service: NotificacionInfoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INotificacionInfo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<NotificacionInfo>) => response.ok),
                map((notificacionInfo: HttpResponse<NotificacionInfo>) => notificacionInfo.body)
            );
        }
        return of(new NotificacionInfo());
    }
}

export const notificacionInfoRoute: Routes = [
    {
        path: '',
        component: NotificacionInfoComponent,
        data: {
            pageTitle: 'NotificacionInfos'
        },
        canActivate: [UserRouteAccessService]
    }
];
