/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { bibliotekTestModule } from '../../../test.module';
import { AdministrativoDeleteDialogComponent } from 'app/entities/administrativo/administrativo-delete-dialog.component';
import { AdministrativoService } from 'app/entities/administrativo/administrativo.service';

describe('Component Tests', () => {
    describe('Administrativo Management Delete Component', () => {
        let comp: AdministrativoDeleteDialogComponent;
        let fixture: ComponentFixture<AdministrativoDeleteDialogComponent>;
        let service: AdministrativoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [AdministrativoDeleteDialogComponent]
            })
                .overrideTemplate(AdministrativoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdministrativoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdministrativoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
