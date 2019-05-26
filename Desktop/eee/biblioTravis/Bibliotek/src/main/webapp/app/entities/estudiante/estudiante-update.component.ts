import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { EstudianteService } from './estudiante.service';

@Component({
    selector: 'jhi-estudiante-update',
    templateUrl: './estudiante-update.component.html'
})
export class EstudianteUpdateComponent implements OnInit {
    estudiante: IEstudiante;
    isSaving: boolean;

    constructor(protected estudianteService: EstudianteService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ estudiante }) => {
            this.estudiante = estudiante;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.estudiante.id !== undefined) {
            this.subscribeToSaveResponse(this.estudianteService.update(this.estudiante));
        } else {
            this.subscribeToSaveResponse(this.estudianteService.create(this.estudiante));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstudiante>>) {
        result.subscribe((res: HttpResponse<IEstudiante>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
