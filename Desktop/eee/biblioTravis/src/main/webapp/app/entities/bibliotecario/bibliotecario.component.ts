import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBibliotecario } from 'app/shared/model/bibliotecario.model';
import { AccountService } from 'app/core';
import { BibliotecarioService } from './bibliotecario.service';

@Component({
    selector: 'jhi-bibliotecario',
    templateUrl: './bibliotecario.component.html'
})
export class BibliotecarioComponent implements OnInit, OnDestroy {
    bibliotecarios: IBibliotecario[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected bibliotecarioService: BibliotecarioService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.bibliotecarioService
            .query()
            .pipe(
                filter((res: HttpResponse<IBibliotecario[]>) => res.ok),
                map((res: HttpResponse<IBibliotecario[]>) => res.body)
            )
            .subscribe(
                (res: IBibliotecario[]) => {
                    this.bibliotecarios = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBibliotecarios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBibliotecario) {
        return item.id;
    }

    registerChangeInBibliotecarios() {
        this.eventSubscriber = this.eventManager.subscribe('bibliotecarioListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
