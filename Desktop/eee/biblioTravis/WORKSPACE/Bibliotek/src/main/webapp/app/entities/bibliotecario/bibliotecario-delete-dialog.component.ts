import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBibliotecario } from 'app/shared/model/bibliotecario.model';
import { BibliotecarioService } from './bibliotecario.service';

@Component({
    selector: 'jhi-bibliotecario-delete-dialog',
    templateUrl: './bibliotecario-delete-dialog.component.html'
})
export class BibliotecarioDeleteDialogComponent {
    bibliotecario: IBibliotecario;

    constructor(
        protected bibliotecarioService: BibliotecarioService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bibliotecarioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bibliotecarioListModification',
                content: 'Deleted an bibliotecario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bibliotecario-delete-popup',
    template: ''
})
export class BibliotecarioDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bibliotecario }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BibliotecarioDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bibliotecario = bibliotecario;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bibliotecario', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bibliotecario', { outlets: { popup: null } }]);
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
