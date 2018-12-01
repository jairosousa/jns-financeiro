import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
        return this.http.post<IPagamento>(this.resourceUrl, pagamento, { observe: 'response' });
    }

    update(pagamento: IPagamento): Observable<EntityResponseType> {
        return this.http.put<IPagamento>(this.resourceUrl, pagamento, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPagamento[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
