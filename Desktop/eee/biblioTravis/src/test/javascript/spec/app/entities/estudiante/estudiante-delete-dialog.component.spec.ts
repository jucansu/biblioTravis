/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { bibliotekTestModule } from '../../../test.module';
import { EstudianteDeleteDialogComponent } from 'app/entities/estudiante/estudiante-delete-dialog.component';
import { EstudianteService } from 'app/entities/estudiante/estudiante.service';

describe('Component Tests', () => {
    describe('Estudiante Management Delete Component', () => {
        let comp: EstudianteDeleteDialogComponent;
        let fixture: ComponentFixture<EstudianteDeleteDialogComponent>;
        let service: EstudianteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [EstudianteDeleteDialogComponent]
            })
                .overrideTemplate(EstudianteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstudianteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstudianteService);
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
