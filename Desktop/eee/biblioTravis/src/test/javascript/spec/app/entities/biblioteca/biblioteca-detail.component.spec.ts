/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { BibliotecaDetailComponent } from 'app/entities/biblioteca/biblioteca-detail.component';
import { Biblioteca } from 'app/shared/model/biblioteca.model';

describe('Component Tests', () => {
    describe('Biblioteca Management Detail Component', () => {
        let comp: BibliotecaDetailComponent;
        let fixture: ComponentFixture<BibliotecaDetailComponent>;
        const route = ({ data: of({ biblioteca: new Biblioteca(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [BibliotecaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BibliotecaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BibliotecaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.biblioteca).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
