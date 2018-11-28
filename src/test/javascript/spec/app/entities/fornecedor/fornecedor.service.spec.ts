/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { FornecedorService } from 'app/entities/fornecedor/fornecedor.service';
import { IFornecedor, Fornecedor, Pessoa } from 'app/shared/model/fornecedor.model';

describe('Service Tests', () => {
    describe('Fornecedor Service', () => {
        let injector: TestBed;
        let service: FornecedorService;
        let httpMock: HttpTestingController;
        let elemDefault: IFornecedor;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(FornecedorService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Fornecedor(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', Pessoa.FISICA, 'AAAAAAA', 'AAAAAAA');
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

            it('should create a Fornecedor', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Fornecedor(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Fornecedor', async () => {
                const returnedFromService = Object.assign(
                    {
                        nome: 'BBBBBB',
                        razaoSocial: 'BBBBBB',
                        telefoneFixo: 'BBBBBB',
                        telefoneCel: 'BBBBBB',
                        pessoa: 'BBBBBB',
                        cnpj: 'BBBBBB',
                        cpf: 'BBBBBB'
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

            it('should return a list of Fornecedor', async () => {
                const returnedFromService = Object.assign(
                    {
                        nome: 'BBBBBB',
                        razaoSocial: 'BBBBBB',
                        telefoneFixo: 'BBBBBB',
                        telefoneCel: 'BBBBBB',
                        pessoa: 'BBBBBB',
                        cnpj: 'BBBBBB',
                        cpf: 'BBBBBB'
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

            it('should delete a Fornecedor', async () => {
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
