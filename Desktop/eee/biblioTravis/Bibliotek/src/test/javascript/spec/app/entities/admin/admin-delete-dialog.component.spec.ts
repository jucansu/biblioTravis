/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { bibliotekTestModule } from '../../../test.module';
import { AdminDeleteDialogComponent } from 'app/entities/admin/admin-delete-dialog.component';
import { AdminService } from 'app/entities/admin/admin.service';

describe('Component Tests', () => {
    describe('Admin Management Delete Component', () => {
        let comp: AdminDeleteDialogComponent;
        let fixture: ComponentFixture<AdminDeleteDialogComponent>;
        let service: AdminService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [AdminDeleteDialogComponent]
            })
                .overrideTemplate(AdminDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdminDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdminService);
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
