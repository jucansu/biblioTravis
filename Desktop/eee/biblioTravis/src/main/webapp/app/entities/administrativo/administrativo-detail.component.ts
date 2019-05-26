import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdministrativo } from 'app/shared/model/administrativo.model';

@Component({
    selector: 'jhi-administrativo-detail',
    templateUrl: './administrativo-detail.component.html'
})
export class AdministrativoDetailComponent implements OnInit {
    administrativo: IAdministrativo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ administrativo }) => {
            this.administrativo = administrativo;
        });
    }

    previousState() {
        window.history.back();
    }
}
