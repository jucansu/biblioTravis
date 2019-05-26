import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAviso } from 'app/shared/model/aviso.model';

@Component({
    selector: 'jhi-aviso-detail',
    templateUrl: './aviso-detail.component.html'
})
export class AvisoDetailComponent implements OnInit {
    aviso: IAviso;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aviso }) => {
            this.aviso = aviso;
        });
    }

    previousState() {
        window.history.back();
    }
}
