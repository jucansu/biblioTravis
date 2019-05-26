import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBibliotecario } from 'app/shared/model/bibliotecario.model';
import { BibliotecarioService } from './bibliotecario.service';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { BibliotecaService } from 'app/entities/biblioteca';

@Component({
    selector: 'jhi-bibliotecario-update',
    templateUrl: './bibliotecario-update.component.html'
})
export class BibliotecarioUpdateComponent implements OnInit {
    bibliotecario: IBibliotecario;
    isSaving: boolean;

    bibliotecas: IBiblioteca[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bibliotecarioService: BibliotecarioService,
        protected bibliotecaService: BibliotecaService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bibliotecario }) => {
            this.bibliotecario = bibliotecario;
        });
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
        if (this.bibliotecario.id !== undefined) {
            this.subscribeToSaveResponse(this.bibliotecarioService.update(this.bibliotecario));
        } else {
            this.subscribeToSaveResponse(this.bibliotecarioService.create(this.bibliotecario));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBibliotecario>>) {
        result.subscribe((res: HttpResponse<IBibliotecario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBibliotecaById(index: number, item: IBiblioteca) {
        return item.id;
    }
}
