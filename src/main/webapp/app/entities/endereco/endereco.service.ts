import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEndereco } from 'app/shared/model/endereco.model';

type EntityResponseType = HttpResponse<IEndereco>;
type EntityArrayResponseType = HttpResponse<IEndereco[]>;

@Injectable({ providedIn: 'root' })
export class EnderecoService {
    public resourceUrl = SERVER_API_URL + 'api/enderecos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/enderecos';

    constructor(private http: HttpClient) {}

    create(endereco: IEndereco): Observable<EntityResponseType> {
        return this.http.post<IEndereco>(this.resourceUrl, endereco, { observe: 'response' });
    }

    update(endereco: IEndereco): Observable<EntityResponseType> {
        return this.http.put<IEndereco>(this.resourceUrl, endereco, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEndereco>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEndereco[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEndereco[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }

    findEndereco(cep: string): Observable<EntityResponseType> {
        return this.http.get<IEndereco>(`${this.resourceUrl}/cep/${cep}`, { observe: 'response' });
    }
}
