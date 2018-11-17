/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PagamentoService } from 'app/entities/pagamento/pagamento.service';
import { IPagamento, Pagamento, FormaPagamento, Status } from 'app/shared/model/pagamento.model';

describe('Service Tests', () => {
    describe('Pagamento Service', () => {
        let injector: TestBed;
        let service: PagamentoService;
        let httpMock: HttpTestingController;
        let elemDefault: IPagamento;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PagamentoService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Pagamento(0, currentDate, currentDate, FormaPagamento.DINHEIRO, 0, 0, Status.PAGO);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        vencimento: currentDate.format(DATE_FORMAT),
                        diaPagamento: currentDate.format(DATE_FORMAT)
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

            it('should create a Pagamento', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        vencimento: currentDate.format(DATE_FORMAT),
                        diaPagamento: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        vencimento: currentDate,
                        diaPagamento: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Pagamento(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Pagamento', async () => {
                const returnedFromService = Object.assign(
                    {
                        vencimento: currentDate.format(DATE_FORMAT),
                        diaPagamento: currentDate.format(DATE_FORMAT),
                        forma: 'BBBBBB',
                        juros: 1,
                        quantidadeParcelas: 1,
                        status: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        vencimento: currentDate,
                        diaPagamento: currentDate
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

            it('should return a list of Pagamento', async () => {
                const returnedFromService = Object.assign(
                    {
                        vencimento: currentDate.format(DATE_FORMAT),
                        diaPagamento: currentDate.format(DATE_FORMAT),
                        forma: 'BBBBBB',
                        juros: 1,
                        quantidadeParcelas: 1,
                        status: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        vencimento: currentDate,
                        diaPagamento: currentDate
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

            it('should delete a Pagamento', async () => {
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
