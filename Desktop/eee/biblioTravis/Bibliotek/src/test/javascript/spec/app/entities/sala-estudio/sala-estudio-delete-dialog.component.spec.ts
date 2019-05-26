/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { bibliotekTestModule } from '../../../test.module';
import { SalaEstudioDeleteDialogComponent } from 'app/entities/sala-estudio/sala-estudio-delete-dialog.component';
import { SalaEstudioService } from 'app/entities/sala-estudio/sala-estudio.service';

describe('Component Tests', () => {
    describe('SalaEstudio Management Delete Component', () => {
        let comp: SalaEstudioDeleteDialogComponent;
        let fixture: ComponentFixture<SalaEstudioDeleteDialogComponent>;
        let service: SalaEstudioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [SalaEstudioDeleteDialogComponent]
            })
                .overrideTemplate(SalaEstudioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalaEstudioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalaEstudioService);
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
