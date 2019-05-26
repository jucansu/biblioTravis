/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { CorreccionDetailComponent } from 'app/entities/correccion/correccion-detail.component';
import { Correccion } from 'app/shared/model/correccion.model';

describe('Component Tests', () => {
    describe('Correccion Management Detail Component', () => {
        let comp: CorreccionDetailComponent;
        let fixture: ComponentFixture<CorreccionDetailComponent>;
        const route = ({ data: of({ correccion: new Correccion(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [CorreccionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CorreccionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CorreccionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.correccion).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
