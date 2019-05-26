import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstudiante } from 'app/shared/model/estudiante.model';

@Component({
    selector: 'jhi-estudiante-detail',
    templateUrl: './estudiante-detail.component.html'
})
export class EstudianteDetailComponent implements OnInit {
    estudiante: IEstudiante;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estudiante }) => {
            this.estudiante = estudiante;
        });
    }

    previousState() {
        window.history.back();
    }
}
