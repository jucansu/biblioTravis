import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';
import { SalaEstudioService } from './sala-estudio.service';
import { IAdministrativo } from 'app/shared/model/administrativo.model';
import { AdministrativoService } from 'app/entities/administrativo';

@Component({
    selector: 'jhi-sala-estudio-update',
    templateUrl: './sala-estudio-update.component.html'
})
export class SalaEstudioUpdateComponent implements OnInit {
    salaEstudio: ISalaEstudio;
    isSaving: boolean;

    administrativos: IAdministrativo[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected salaEstudioService: SalaEstudioService,
        protected administrativoService: AdministrativoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salaEstudio }) => {
            this.salaEstudio = salaEstudio;
        });
        this.administrativoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdministrativo[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdministrativo[]>) => response.body)
            )
            .subscribe((res: IAdministrativo[]) => (this.administrativos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.salaEstudio.id !== undefined) {
            this.subscribeToSaveResponse(this.salaEstudioService.update(this.salaEstudio));
        } else {
            this.subscribeToSaveResponse(this.salaEstudioService.create(this.salaEstudio));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalaEstudio>>) {
        result.subscribe((res: HttpResponse<ISalaEstudio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdministrativoById(index: number, item: IAdministrativo) {
        return item.id;
    }
}
