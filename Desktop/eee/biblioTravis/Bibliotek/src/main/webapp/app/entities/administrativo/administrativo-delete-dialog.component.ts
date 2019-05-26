import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdministrativo } from 'app/shared/model/administrativo.model';
import { AdministrativoService } from './administrativo.service';

@Component({
    selector: 'jhi-administrativo-delete-dialog',
    templateUrl: './administrativo-delete-dialog.component.html'
})
export class AdministrativoDeleteDialogComponent {
    administrativo: IAdministrativo;

    constructor(
        protected administrativoService: AdministrativoService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.administrativoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'administrativoListModification',
                content: 'Deleted an administrativo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-administrativo-delete-popup',
    template: ''
})
export class AdministrativoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ administrativo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AdministrativoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.administrativo = administrativo;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/administrativo', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/administrativo', { outlets: { popup: null } }]);
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
