/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { BibliotecarioUpdateComponent } from 'app/entities/bibliotecario/bibliotecario-update.component';
import { BibliotecarioService } from 'app/entities/bibliotecario/bibliotecario.service';
import { Bibliotecario } from 'app/shared/model/bibliotecario.model';

describe('Component Tests', () => {
    describe('Bibliotecario Management Update Component', () => {
        let comp: BibliotecarioUpdateComponent;
        let fixture: ComponentFixture<BibliotecarioUpdateComponent>;
        let service: BibliotecarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [BibliotecarioUpdateComponent]
            })
                .overrideTemplate(BibliotecarioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BibliotecarioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BibliotecarioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bibliotecario(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bibliotecario = entity;
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
                    const entity = new Bibliotecario();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bibliotecario = entity;
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
