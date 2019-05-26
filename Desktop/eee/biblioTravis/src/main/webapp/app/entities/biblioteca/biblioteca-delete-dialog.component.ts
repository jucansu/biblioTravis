import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { BibliotecaService } from './biblioteca.service';

@Component({
    selector: 'jhi-biblioteca-delete-dialog',
    templateUrl: './biblioteca-delete-dialog.component.html'
})
export class BibliotecaDeleteDialogComponent {
    biblioteca: IBiblioteca;

    constructor(
        protected bibliotecaService: BibliotecaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bibliotecaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bibliotecaListModification',
                content: 'Deleted an biblioteca'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-biblioteca-delete-popup',
    template: ''
})
export class BibliotecaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ biblioteca }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BibliotecaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.biblioteca = biblioteca;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/biblioteca', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/biblioteca', { outlets: { popup: null } }]);
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
