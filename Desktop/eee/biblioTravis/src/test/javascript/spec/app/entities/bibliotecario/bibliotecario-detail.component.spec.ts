/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { BibliotecarioDetailComponent } from 'app/entities/bibliotecario/bibliotecario-detail.component';
import { Bibliotecario } from 'app/shared/model/bibliotecario.model';

describe('Component Tests', () => {
    describe('Bibliotecario Management Detail Component', () => {
        let comp: BibliotecarioDetailComponent;
        let fixture: ComponentFixture<BibliotecarioDetailComponent>;
        const route = ({ data: of({ bibliotecario: new Bibliotecario(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [BibliotecarioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BibliotecarioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BibliotecarioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bibliotecario).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
