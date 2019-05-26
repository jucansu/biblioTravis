import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAviso } from 'app/shared/model/aviso.model';
import { AccountService } from 'app/core';
import { AvisoService } from './aviso.service';

@Component({
    selector: 'jhi-aviso',
    templateUrl: './aviso.component.html'
})
export class AvisoComponent implements OnInit, OnDestroy {
    avisos: IAviso[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected avisoService: AvisoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.avisoService
            .query()
            .pipe(
                filter((res: HttpResponse<IAviso[]>) => res.ok),
                map((res: HttpResponse<IAviso[]>) => res.body)
            )
            .subscribe(
                (res: IAviso[]) => {
                    this.avisos = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAvisos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAviso) {
        return item.id;
    }

    registerChangeInAvisos() {
        this.eventSubscriber = this.eventManager.subscribe('avisoListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
