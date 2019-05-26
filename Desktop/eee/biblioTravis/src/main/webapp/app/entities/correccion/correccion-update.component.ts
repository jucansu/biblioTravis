import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICorreccion } from 'app/shared/model/correccion.model';
import { CorreccionService } from './correccion.service';
import { IBibliotecario } from 'app/shared/model/bibliotecario.model';
import { BibliotecarioService } from 'app/entities/bibliotecario';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { BibliotecaService } from 'app/entities/biblioteca';

@Component({
    selector: 'jhi-correccion-update',
    templateUrl: './correccion-update.component.html'
})
export class CorreccionUpdateComponent implements OnInit {
    correccion: ICorreccion;
    isSaving: boolean;

    bibliotecarios: IBibliotecario[];

    bibliotecas: IBiblioteca[];
    fechaDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected correccionService: CorreccionService,
        protected bibliotecarioService: BibliotecarioService,
        protected bibliotecaService: BibliotecaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ correccion }) => {
            this.correccion = correccion;
        });
        this.bibliotecarioService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBibliotecario[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBibliotecario[]>) => response.body)
            )
            .subscribe((res: IBibliotecario[]) => (this.bibliotecarios = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.bibliotecaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBiblioteca[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBiblioteca[]>) => response.body)
            )
            .subscribe((res: IBiblioteca[]) => (this.bibliotecas = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.correccion.id !== undefined) {
            this.subscribeToSaveResponse(this.correccionService.update(this.correccion));
        } else {
            this.subscribeToSaveResponse(this.correccionService.create(this.correccion));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICorreccion>>) {
        result.subscribe((res: HttpResponse<ICorreccion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBibliotecarioById(index: number, item: IBibliotecario) {
        return item.id;
    }

    trackBibliotecaById(index: number, item: IBiblioteca) {
        return item.id;
    }
}
