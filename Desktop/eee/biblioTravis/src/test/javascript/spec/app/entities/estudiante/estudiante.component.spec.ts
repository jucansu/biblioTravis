/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { bibliotekTestModule } from '../../../test.module';
import { EstudianteComponent } from 'app/entities/estudiante/estudiante.component';
import { EstudianteService } from 'app/entities/estudiante/estudiante.service';
import { Estudiante } from 'app/shared/model/estudiante.model';

describe('Component Tests', () => {
    describe('Estudiante Management Component', () => {
        let comp: EstudianteComponent;
        let fixture: ComponentFixture<EstudianteComponent>;
        let service: EstudianteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [EstudianteComponent],
                providers: []
            })
                .overrideTemplate(EstudianteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EstudianteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstudianteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Estudiante(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.estudiantes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
