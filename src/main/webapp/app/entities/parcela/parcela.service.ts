import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParcela } from 'app/shared/model/parcela.model';

type EntityResponseType = HttpResponse<IParcela>;
type EntityArrayResponseType = HttpResponse<IParcela[]>;

@Injectable({ providedIn: 'root' })
export class ParcelaService {
    public resourceUrl = SERVER_API_URL + 'api/parcelas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/parcelas';

    parcelas: IParcela[] = [];

    constructor(private http: HttpClient) {}

    adcList(parcela: IParcela) {
        this.parcelas.push(parcela);
    }

    create(parcela: IParcela): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(parcela);
        return this.http
            .post<IParcela>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(parcela: IParcela): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(parcela);
        return this.http
            .put<IParcela>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IParcela>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByPagamento(id: number): Observable<EntityArrayResponseType> {
        return this.http
            .get<IParcela[]>(`${this.resourceUrl}/pagamento/${id}`, { observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IParcela[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IParcela[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(parcela: IParcela): IParcela {
        const copy: IParcela = Object.assign({}, parcela, {
            dataVencimento:
                parcela.dataVencimento != null && parcela.dataVencimento.isValid() ? parcela.dataVencimento.format(DATE_FORMAT) : null,
            dataPagamento:
                parcela.dataPagamento != null && parcela.dataPagamento.isValid() ? parcela.dataPagamento.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dataVencimento = res.body.dataVencimento != null ? moment(res.body.dataVencimento) : null;
            res.body.dataPagamento = res.body.dataPagamento != null ? moment(res.body.dataPagamento) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((parcela: IParcela) => {
                parcela.dataVencimento = parcela.dataVencimento != null ? moment(parcela.dataVencimento) : null;
                parcela.dataPagamento = parcela.dataPagamento != null ? moment(parcela.dataPagamento) : null;
            });
        }
        return res;
    }
}
