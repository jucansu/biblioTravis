/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { bibliotekTestModule } from '../../../test.module';
import { AvisoComponent } from 'app/entities/aviso/aviso.component';
import { AvisoService } from 'app/entities/aviso/aviso.service';
import { Aviso } from 'app/shared/model/aviso.model';

describe('Component Tests', () => {
    describe('Aviso Management Component', () => {
        let comp: AvisoComponent;
        let fixture: ComponentFixture<AvisoComponent>;
        let service: AvisoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [AvisoComponent],
                providers: []
            })
                .overrideTemplate(AvisoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AvisoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvisoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Aviso(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.avisos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
