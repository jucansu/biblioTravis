/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { EstudianteDetailComponent } from 'app/entities/estudiante/estudiante-detail.component';
import { Estudiante } from 'app/shared/model/estudiante.model';

describe('Component Tests', () => {
    describe('Estudiante Management Detail Component', () => {
        let comp: EstudianteDetailComponent;
        let fixture: ComponentFixture<EstudianteDetailComponent>;
        const route = ({ data: of({ estudiante: new Estudiante(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [EstudianteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EstudianteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EstudianteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.estudiante).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
