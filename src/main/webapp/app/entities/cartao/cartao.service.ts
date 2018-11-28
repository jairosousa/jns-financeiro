import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICartao } from 'app/shared/model/cartao.model';

type EntityResponseType = HttpResponse<ICartao>;
type EntityArrayResponseType = HttpResponse<ICartao[]>;

@Injectable({ providedIn: 'root' })
export class CartaoService {
    public resourceUrl = SERVER_API_URL + 'api/cartaos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/cartaos';

    constructor(private http: HttpClient) {}

    create(cartao: ICartao): Observable<EntityResponseType> {
        return this.http.post<ICartao>(this.resourceUrl, cartao, { observe: 'response' });
    }

    update(cartao: ICartao): Observable<EntityResponseType> {
        return this.http.put<ICartao>(this.resourceUrl, cartao, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICartao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICartao[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICartao[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
