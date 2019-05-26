/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { bibliotekTestModule } from '../../../test.module';
import { BibliotecarioComponent } from 'app/entities/bibliotecario/bibliotecario.component';
import { BibliotecarioService } from 'app/entities/bibliotecario/bibliotecario.service';
import { Bibliotecario } from 'app/shared/model/bibliotecario.model';

describe('Component Tests', () => {
    describe('Bibliotecario Management Component', () => {
        let comp: BibliotecarioComponent;
        let fixture: ComponentFixture<BibliotecarioComponent>;
        let service: BibliotecarioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [BibliotecarioComponent],
                providers: []
            })
                .overrideTemplate(BibliotecarioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BibliotecarioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BibliotecarioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bibliotecario(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bibliotecarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
