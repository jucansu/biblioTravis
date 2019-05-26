import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAdministrativo } from 'app/shared/model/administrativo.model';
import { AccountService } from 'app/core';
import { AdministrativoService } from './administrativo.service';

@Component({
    selector: 'jhi-administrativo',
    templateUrl: './administrativo.component.html'
})
export class AdministrativoComponent implements OnInit, OnDestroy {
    administrativos: IAdministrativo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected administrativoService: AdministrativoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.administrativoService
            .query()
            .pipe(
                filter((res: HttpResponse<IAdministrativo[]>) => res.ok),
                map((res: HttpResponse<IAdministrativo[]>) => res.body)
            )
            .subscribe(
                (res: IAdministrativo[]) => {
                    this.administrativos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAdministrativos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAdministrativo) {
        return item.id;
    }

    registerChangeInAdministrativos() {
        this.eventSubscriber = this.eventManager.subscribe('administrativoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
