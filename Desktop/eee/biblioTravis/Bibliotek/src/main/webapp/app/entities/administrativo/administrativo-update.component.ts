import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAdministrativo } from 'app/shared/model/administrativo.model';
import { AdministrativoService } from './administrativo.service';

@Component({
    selector: 'jhi-administrativo-update',
    templateUrl: './administrativo-update.component.html'
})
export class AdministrativoUpdateComponent implements OnInit {
    administrativo: IAdministrativo;
    isSaving: boolean;

    constructor(protected administrativoService: AdministrativoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ administrativo }) => {
            this.administrativo = administrativo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.administrativo.id !== undefined) {
            this.subscribeToSaveResponse(this.administrativoService.update(this.administrativo));
        } else {
            this.subscribeToSaveResponse(this.administrativoService.create(this.administrativo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdministrativo>>) {
        result.subscribe((res: HttpResponse<IAdministrativo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
