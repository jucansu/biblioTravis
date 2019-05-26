/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { CorreccionUpdateComponent } from 'app/entities/correccion/correccion-update.component';
import { CorreccionService } from 'app/entities/correccion/correccion.service';
import { Correccion } from 'app/shared/model/correccion.model';

describe('Component Tests', () => {
    describe('Correccion Management Update Component', () => {
        let comp: CorreccionUpdateComponent;
        let fixture: ComponentFixture<CorreccionUpdateComponent>;
        let service: CorreccionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [CorreccionUpdateComponent]
            })
                .overrideTemplate(CorreccionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CorreccionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CorreccionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Correccion(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.correccion = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Correccion();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.correccion = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
