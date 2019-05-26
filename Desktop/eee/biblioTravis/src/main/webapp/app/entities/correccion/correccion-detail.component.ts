import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICorreccion } from 'app/shared/model/correccion.model';

@Component({
    selector: 'jhi-correccion-detail',
    templateUrl: './correccion-detail.component.html'
})
export class CorreccionDetailComponent implements OnInit {
    correccion: ICorreccion;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ correccion }) => {
            this.correccion = correccion;
        });
    }

    previousState() {
        window.history.back();
    }
}
