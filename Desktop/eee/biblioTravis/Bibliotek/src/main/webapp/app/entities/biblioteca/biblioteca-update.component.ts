import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { BibliotecaService } from './biblioteca.service';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { EstudianteService } from 'app/entities/estudiante';

@Component({
    selector: 'jhi-biblioteca-update',
    templateUrl: './biblioteca-update.component.html'
})
export class BibliotecaUpdateComponent implements OnInit {
    biblioteca: IBiblioteca;
    isSaving: boolean;

    estudiantes: IEstudiante[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bibliotecaService: BibliotecaService,
        protected estudianteService: EstudianteService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ biblioteca }) => {
            this.biblioteca = biblioteca;
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
        if (this.biblioteca.id !== undefined) {
            this.subscribeToSaveResponse(this.bibliotecaService.update(this.biblioteca));
        } else {
            this.subscribeToSaveResponse(this.bibliotecaService.create(this.biblioteca));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBiblioteca>>) {
        result.subscribe((res: HttpResponse<IBiblioteca>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
