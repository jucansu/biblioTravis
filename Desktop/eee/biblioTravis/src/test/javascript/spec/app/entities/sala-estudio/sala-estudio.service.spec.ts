/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { SalaEstudioService } from 'app/entities/sala-estudio/sala-estudio.service';
import { ISalaEstudio, SalaEstudio, TipoZona } from 'app/shared/model/sala-estudio.model';

describe('Service Tests', () => {
    describe('SalaEstudio Service', () => {
        let injector: TestBed;
        let service: SalaEstudioService;
        let httpMock: HttpTestingController;
        let elemDefault: ISalaEstudio;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SalaEstudioService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new SalaEstudio(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, TipoZona.NERVION, 0, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a SalaEstudio', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new SalaEstudio(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a SalaEstudio', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombre: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        valoracion: 'BBBBBB',
                        plazasTotales: 1,
                        zona: 'BBBBBB',
                        numEnchufes: 1,
                        hablar: true
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of SalaEstudio', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombre: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        valoracion: 'BBBBBB',
                        plazasTotales: 1,
                        zona: 'BBBBBB',
                        numEnchufes: 1,
                        hablar: true
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a SalaEstudio', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
