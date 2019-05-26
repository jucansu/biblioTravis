import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAviso } from 'app/shared/model/aviso.model';
import { AvisoService } from './aviso.service';

@Component({
    selector: 'jhi-aviso-delete-dialog',
    templateUrl: './aviso-delete-dialog.component.html'
})
export class AvisoDeleteDialogComponent {
    aviso: IAviso;

    constructor(protected avisoService: AvisoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.avisoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'avisoListModification',
                content: 'Deleted an aviso'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-aviso-delete-popup',
    template: ''
})
export class AvisoDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aviso }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AvisoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.aviso = aviso;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/aviso', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/aviso', { outlets: { popup: null } }]);
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
