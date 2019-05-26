import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICorreccion } from 'app/shared/model/correccion.model';
import { AccountService } from 'app/core';
import { CorreccionService } from './correccion.service';

@Component({
    selector: 'jhi-correccion',
    templateUrl: './correccion.component.html'
})
export class CorreccionComponent implements OnInit, OnDestroy {
    correccions: ICorreccion[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected correccionService: CorreccionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.correccionService
            .query()
            .pipe(
                filter((res: HttpResponse<ICorreccion[]>) => res.ok),
                map((res: HttpResponse<ICorreccion[]>) => res.body)
            )
            .subscribe(
                (res: ICorreccion[]) => {
                    this.correccions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCorreccions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICorreccion) {
        return item.id;
    }

    registerChangeInCorreccions() {
        this.eventSubscriber = this.eventManager.subscribe('correccionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
