/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { SalaEstudioDetailComponent } from 'app/entities/sala-estudio/sala-estudio-detail.component';
import { SalaEstudio } from 'app/shared/model/sala-estudio.model';

describe('Component Tests', () => {
    describe('SalaEstudio Management Detail Component', () => {
        let comp: SalaEstudioDetailComponent;
        let fixture: ComponentFixture<SalaEstudioDetailComponent>;
        const route = ({ data: of({ salaEstudio: new SalaEstudio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [SalaEstudioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SalaEstudioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalaEstudioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.salaEstudio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
