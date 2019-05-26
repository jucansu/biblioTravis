import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBiblioteca } from 'app/shared/model/biblioteca.model';

@Component({
    selector: 'jhi-biblioteca-detail',
    templateUrl: './biblioteca-detail.component.html'
})
export class BibliotecaDetailComponent implements OnInit {
    biblioteca: IBiblioteca;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ biblioteca }) => {
            this.biblioteca = biblioteca;
        });
    }

    previousState() {
        window.history.back();
    }
}
