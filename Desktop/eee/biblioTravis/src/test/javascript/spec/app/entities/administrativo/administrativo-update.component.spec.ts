/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { AdministrativoUpdateComponent } from 'app/entities/administrativo/administrativo-update.component';
import { AdministrativoService } from 'app/entities/administrativo/administrativo.service';
import { Administrativo } from 'app/shared/model/administrativo.model';

describe('Component Tests', () => {
    describe('Administrativo Management Update Component', () => {
        let comp: AdministrativoUpdateComponent;
        let fixture: ComponentFixture<AdministrativoUpdateComponent>;
        let service: AdministrativoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [AdministrativoUpdateComponent]
            })
                .overrideTemplate(AdministrativoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdministrativoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdministrativoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Administrativo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.administrativo = entity;
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
                    const entity = new Administrativo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.administrativo = entity;
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
