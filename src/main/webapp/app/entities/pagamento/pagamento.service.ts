import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPagamento } from 'app/shared/model/pagamento.model';

type EntityResponseType = HttpResponse<IPagamento>;
type EntityArrayResponseType = HttpResponse<IPagamento[]>;

@Injectable({ providedIn: 'root' })
export class PagamentoService {
    public resourceUrl = SERVER_API_URL + 'api/pagamentos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/pagamentos';

    constructor(private http: HttpClient) {}

    create(pagamento: IPagamento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pagamento);
        return this.http
            .post<IPagamento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pagamento: IPagamento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pagamento);
        return this.http
            .put<IPagamento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPagamento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPagamento[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(pagamento: IPagamento): IPagamento {
        const copy: IPagamento = Object.assign({}, pagamento, {
            dataPrimeiroVencimento:
                pagamento.dataPrimeiroVencimento != null && pagamento.dataPrimeiroVencimento.isValid()
                    ? pagamento.dataPrimeiroVencimento.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataPrimeiroVencimento = res.body.dataPrimeiroVencimento != null ? moment(res.body.dataPrimeiroVencimento) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((pagamento: IPagamento) => {
                pagamento.dataPrimeiroVencimento =
                    pagamento.dataPrimeiroVencimento != null ? moment(pagamento.dataPrimeiroVencimento) : null;
            });
        }
        return res;
    }
}
