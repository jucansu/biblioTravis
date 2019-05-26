/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { bibliotekTestModule } from '../../../test.module';
import { AdministrativoComponent } from 'app/entities/administrativo/administrativo.component';
import { AdministrativoService } from 'app/entities/administrativo/administrativo.service';
import { Administrativo } from 'app/shared/model/administrativo.model';

describe('Component Tests', () => {
    describe('Administrativo Management Component', () => {
        let comp: AdministrativoComponent;
        let fixture: ComponentFixture<AdministrativoComponent>;
        let service: AdministrativoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [AdministrativoComponent],
                providers: []
            })
                .overrideTemplate(AdministrativoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AdministrativoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdministrativoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Administrativo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.administrativos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
