/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { bibliotekTestModule } from '../../../test.module';
import { CorreccionComponent } from 'app/entities/correccion/correccion.component';
import { CorreccionService } from 'app/entities/correccion/correccion.service';
import { Correccion } from 'app/shared/model/correccion.model';

describe('Component Tests', () => {
    describe('Correccion Management Component', () => {
        let comp: CorreccionComponent;
        let fixture: ComponentFixture<CorreccionComponent>;
        let service: CorreccionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [CorreccionComponent],
                providers: []
            })
                .overrideTemplate(CorreccionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CorreccionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CorreccionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Correccion(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.correccions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
