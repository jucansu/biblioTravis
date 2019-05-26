import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReporte } from 'app/shared/model/reporte.model';
import { ReporteService } from './reporte.service';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { EstudianteService } from 'app/entities/estudiante';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { BibliotecaService } from 'app/entities/biblioteca';
import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';
import { SalaEstudioService } from 'app/entities/sala-estudio';

@Component({
    selector: 'jhi-reporte-update',
    templateUrl: './reporte-update.component.html'
})
export class ReporteUpdateComponent implements OnInit {
    reporte: IReporte;
    isSaving: boolean;

    estudiantes: IEstudiante[];

    bibliotecas: IBiblioteca[];

    salaestudios: ISalaEstudio[];
    fechaDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reporteService: ReporteService,
        protected estudianteService: EstudianteService,
        protected bibliotecaService: BibliotecaService,
        protected salaEstudioService: SalaEstudioService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reporte }) => {
            this.reporte = reporte;
        });
        this.estudianteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEstudiante[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEstudiante[]>) => response.body)
            )
            .subscribe((res: IEstudiante[]) => (this.estudiantes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.bibliotecaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBiblioteca[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBiblioteca[]>) => response.body)
            )
            .subscribe((res: IBiblioteca[]) => (this.bibliotecas = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.salaEstudioService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISalaEstudio[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISalaEstudio[]>) => response.body)
            )
            .subscribe((res: ISalaEstudio[]) => (this.salaestudios = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reporte.id !== undefined) {
            this.subscribeToSaveResponse(this.reporteService.update(this.reporte));
        } else {
            this.subscribeToSaveResponse(this.reporteService.create(this.reporte));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReporte>>) {
        result.subscribe((res: HttpResponse<IReporte>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBibliotecaById(index: number, item: IBiblioteca) {
        return item.id;
    }

    trackSalaEstudioById(index: number, item: ISalaEstudio) {
        return item.id;
    }
}
