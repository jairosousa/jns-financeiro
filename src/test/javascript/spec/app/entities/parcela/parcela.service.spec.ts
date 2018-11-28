/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ParcelaService } from 'app/entities/parcela/parcela.service';
import { IParcela, Parcela, FormaPagamento, Status } from 'app/shared/model/parcela.model';

describe('Service Tests', () => {
    describe('Parcela Service', () => {
        let injector: TestBed;
        let service: ParcelaService;
        let httpMock: HttpTestingController;
        let elemDefault: IParcela;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ParcelaService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Parcela(0, currentDate, currentDate, 0, 0, 0, 0, FormaPagamento.DINHEIRO, Status.PAGO);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        dataPagamento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Parcela', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        dataPagamento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataVencimento: currentDate,
                        dataPagamento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Parcela(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Parcela', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        dataPagamento: currentDate.format(DATE_FORMAT),
                        numero: 1,
                        valor: 1,
                        juros: 1,
                        total: 1,
                        forma: 'BBBBBB',
                        status: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dataVencimento: currentDate,
                        dataPagamento: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Parcela', async () => {
                const returnedFromService = Object.assign(
                    {
                        dataVencimento: currentDate.format(DATE_FORMAT),
                        dataPagamento: currentDate.format(DATE_FORMAT),
                        numero: 1,
                        valor: 1,
                        juros: 1,
                        total: 1,
                        forma: 'BBBBBB',
                        status: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dataVencimento: currentDate,
                        dataPagamento: currentDate
                    },
                    returnedFromService
                );
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

            it('should delete a Parcela', async () => {
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