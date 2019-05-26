/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { BibliotecaUpdateComponent } from 'app/entities/biblioteca/biblioteca-update.component';
import { BibliotecaService } from 'app/entities/biblioteca/biblioteca.service';
import { Biblioteca } from 'app/shared/model/biblioteca.model';

describe('Component Tests', () => {
    describe('Biblioteca Management Update Component', () => {
        let comp: BibliotecaUpdateComponent;
        let fixture: ComponentFixture<BibliotecaUpdateComponent>;
        let service: BibliotecaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [BibliotecaUpdateComponent]
            })
                .overrideTemplate(BibliotecaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BibliotecaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BibliotecaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Biblioteca(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.biblioteca = entity;
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
                    const entity = new Biblioteca();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.biblioteca = entity;
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
