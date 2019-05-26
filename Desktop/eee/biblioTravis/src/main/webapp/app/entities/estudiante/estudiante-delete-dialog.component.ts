import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEstudiante } from 'app/shared/model/estudiante.model';
import { EstudianteService } from './estudiante.service';

@Component({
    selector: 'jhi-estudiante-delete-dialog',
    templateUrl: './estudiante-delete-dialog.component.html'
})
export class EstudianteDeleteDialogComponent {
    estudiante: IEstudiante;

    constructor(
        protected estudianteService: EstudianteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estudianteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'estudianteListModification',
                content: 'Deleted an estudiante'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estudiante-delete-popup',
    template: ''
})
export class EstudianteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ estudiante }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EstudianteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.estudiante = estudiante;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/estudiante', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/estudiante', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
