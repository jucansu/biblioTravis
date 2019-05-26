import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAviso } from 'app/shared/model/aviso.model';
import { AvisoService } from './aviso.service';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { EstudianteService } from 'app/entities/estudiante';

@Component({
    selector: 'jhi-aviso-update',
    templateUrl: './aviso-update.component.html'
})
export class AvisoUpdateComponent implements OnInit {
    aviso: IAviso;
    isSaving: boolean;

    estudiantes: IEstudiante[];
    fechaDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected avisoService: AvisoService,
        protected estudianteService: EstudianteService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ aviso }) => {
            this.aviso = aviso;
        });
        this.estudianteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstudiante[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstudiante[]>) => response.body)
            )
            .subscribe((res: IEstudiante[]) => (this.estudiantes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.aviso.id !== undefined) {
            this.subscribeToSaveResponse(this.avisoService.update(this.aviso));
        } else {
            this.subscribeToSaveResponse(this.avisoService.create(this.aviso));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAviso>>) {
        result.subscribe((res: HttpResponse<IAviso>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEstudianteById(index: number, item: IEstudiante) {
        return item.id;
    }
}
