/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { bibliotekTestModule } from '../../../test.module';
import { AvisoDeleteDialogComponent } from 'app/entities/aviso/aviso-delete-dialog.component';
import { AvisoService } from 'app/entities/aviso/aviso.service';

describe('Component Tests', () => {
    describe('Aviso Management Delete Component', () => {
        let comp: AvisoDeleteDialogComponent;
        let fixture: ComponentFixture<AvisoDeleteDialogComponent>;
        let service: AvisoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [AvisoDeleteDialogComponent]
            })
                .overrideTemplate(AvisoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AvisoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvisoService);
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
