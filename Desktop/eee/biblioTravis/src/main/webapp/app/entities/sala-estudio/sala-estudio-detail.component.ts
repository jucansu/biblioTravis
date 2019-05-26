import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';

@Component({
    selector: 'jhi-sala-estudio-detail',
    templateUrl: './sala-estudio-detail.component.html'
})
export class SalaEstudioDetailComponent implements OnInit {
    salaEstudio: ISalaEstudio;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salaEstudio }) => {
            this.salaEstudio = salaEstudio;
        });
    }

    previousState() {
        window.history.back();
    }
}
