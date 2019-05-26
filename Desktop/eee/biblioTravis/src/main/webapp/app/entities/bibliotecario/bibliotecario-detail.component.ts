import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBibliotecario } from 'app/shared/model/bibliotecario.model';

@Component({
    selector: 'jhi-bibliotecario-detail',
    templateUrl: './bibliotecario-detail.component.html'
})
export class BibliotecarioDetailComponent implements OnInit {
    bibliotecario: IBibliotecario;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bibliotecario }) => {
            this.bibliotecario = bibliotecario;
        });
    }

    previousState() {
        window.history.back();
    }
}
