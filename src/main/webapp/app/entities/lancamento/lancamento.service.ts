import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILancamento } from 'app/shared/model/lancamento.model';

type EntityResponseType = HttpResponse<ILancamento>;
type EntityArrayResponseType = HttpResponse<ILancamento[]>;

@Injectable({ providedIn: 'root' })
export class LancamentoService {
    public resourceUrl = SERVER_API_URL + 'api/lancamentos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/lancamentos';

    constructor(private http: HttpClient) {}

    create(lancamento: ILancamento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lancamento);
        return this.http
            .post<ILancamento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(lancamento: ILancamento): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(lancamento);
        return this.http
            .put<ILancamento>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILancamento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILancamento[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILancamento[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(lancamento: ILancamento): ILancamento {
        const copy: ILancamento = Object.assign({}, lancamento, {
            data: lancamento.data != null && lancamento.data.isValid() ? lancamento.data.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.data = res.body.data != null ? moment(res.body.data) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((lancamento: ILancamento) => {
                lancamento.data = lancamento.data != null ? moment(lancamento.data) : null;
            });
        }
        return res;
    }
}
