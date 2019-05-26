import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';
import { SalaEstudioService } from './sala-estudio.service';

@Component({
    selector: 'jhi-sala-estudio-delete-dialog',
    templateUrl: './sala-estudio-delete-dialog.component.html'
})
export class SalaEstudioDeleteDialogComponent {
    salaEstudio: ISalaEstudio;

    constructor(
        protected salaEstudioService: SalaEstudioService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.salaEstudioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'salaEstudioListModification',
                content: 'Deleted an salaEstudio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sala-estudio-delete-popup',
    template: ''
})
export class SalaEstudioDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ salaEstudio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SalaEstudioDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.salaEstudio = salaEstudio;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/sala-estudio', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/sala-estudio', { outlets: { popup: null } }]);
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
