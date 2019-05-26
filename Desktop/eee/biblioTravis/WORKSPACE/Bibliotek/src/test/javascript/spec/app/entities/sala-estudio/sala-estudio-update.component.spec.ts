/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { SalaEstudioUpdateComponent } from 'app/entities/sala-estudio/sala-estudio-update.component';
import { SalaEstudioService } from 'app/entities/sala-estudio/sala-estudio.service';
import { SalaEstudio } from 'app/shared/model/sala-estudio.model';

describe('Component Tests', () => {
    describe('SalaEstudio Management Update Component', () => {
        let comp: SalaEstudioUpdateComponent;
        let fixture: ComponentFixture<SalaEstudioUpdateComponent>;
        let service: SalaEstudioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [SalaEstudioUpdateComponent]
            })
                .overrideTemplate(SalaEstudioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SalaEstudioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalaEstudioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SalaEstudio(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salaEstudio = entity;
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
                    const entity = new SalaEstudio();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.salaEstudio = entity;
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
