/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { bibliotekTestModule } from '../../../test.module';
import { CorreccionDeleteDialogComponent } from 'app/entities/correccion/correccion-delete-dialog.component';
import { CorreccionService } from 'app/entities/correccion/correccion.service';

describe('Component Tests', () => {
    describe('Correccion Management Delete Component', () => {
        let comp: CorreccionDeleteDialogComponent;
        let fixture: ComponentFixture<CorreccionDeleteDialogComponent>;
        let service: CorreccionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [CorreccionDeleteDialogComponent]
            })
                .overrideTemplate(CorreccionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CorreccionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CorreccionService);
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
