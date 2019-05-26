import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IActor } from 'app/shared/model/actor.model';
import { AccountService } from 'app/core';
import { ActorService } from './actor.service';

@Component({
    selector: 'jhi-actor',
    templateUrl: './actor.component.html'
})
export class ActorComponent implements OnInit, OnDestroy {
    actors: IActor[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected actorService: ActorService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.actorService
            .query()
            .pipe(
                filter((res: HttpResponse<IActor[]>) => res.ok),
                map((res: HttpResponse<IActor[]>) => res.body)
            )
            .subscribe(
                (res: IActor[]) => {
                    this.actors = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInActors();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IActor) {
        return item.id;
    }

    registerChangeInActors() {
        this.eventSubscriber = this.eventManager.subscribe('actorListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
