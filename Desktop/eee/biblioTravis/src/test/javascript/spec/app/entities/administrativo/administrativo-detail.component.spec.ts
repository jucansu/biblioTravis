/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { bibliotekTestModule } from '../../../test.module';
import { AdministrativoDetailComponent } from 'app/entities/administrativo/administrativo-detail.component';
import { Administrativo } from 'app/shared/model/administrativo.model';

describe('Component Tests', () => {
    describe('Administrativo Management Detail Component', () => {
        let comp: AdministrativoDetailComponent;
        let fixture: ComponentFixture<AdministrativoDetailComponent>;
        const route = ({ data: of({ administrativo: new Administrativo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [bibliotekTestModule],
                declarations: [AdministrativoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AdministrativoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdministrativoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.administrativo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
