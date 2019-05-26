import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICorreccion } from 'app/shared/model/correccion.model';
import { CorreccionService } from './correccion.service';

@Component({
    selector: 'jhi-correccion-delete-dialog',
    templateUrl: './correccion-delete-dialog.component.html'
})
export class CorreccionDeleteDialogComponent {
    correccion: ICorreccion;

    constructor(
        protected correccionService: CorreccionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.correccionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'correccionListModification',
                content: 'Deleted an correccion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-correccion-delete-popup',
    template: ''
})
export class CorreccionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ correccion }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CorreccionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.correccion = correccion;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/correccion', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/correccion', { outlets: { popup: null } }]);
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
