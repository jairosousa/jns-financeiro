import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IParcela } from 'app/shared/model/parcela.model';
import { ParcelaService } from './parcela.service';
import { ICartao } from 'app/shared/model/cartao.model';
import { CartaoService } from 'app/entities/cartao';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from 'app/entities/pagamento';

@Component({
    selector: 'jhi-parcela-update',
    templateUrl: './parcela-update.component.html'
})
export class ParcelaUpdateComponent implements OnInit {
    parcela: IParcela;
    isSaving: boolean;

    cartaos: ICartao[];

    pagamentos: IPagamento[];
    dataVencimentoDp: any;
    dataPagamentoDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private parcelaService: ParcelaService,
        private cartaoService: CartaoService,
        private pagamentoService: PagamentoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parcela }) => {
            this.parcela = parcela;
        });
        this.cartaoService.query({ filter: 'parcela-is-null' }).subscribe(
            (res: HttpResponse<ICartao[]>) => {
                if (!this.parcela.cartaoId) {
                    this.cartaos = res.body;
                } else {
                    this.cartaoService.find(this.parcela.cartaoId).subscribe(
                        (subRes: HttpResponse<ICartao>) => {
                            this.cartaos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.pagamentoService.query().subscribe(
            (res: HttpResponse<IPagamento[]>) => {
                this.pagamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parcela.id !== undefined) {
            this.subscribeToSaveResponse(this.parcelaService.update(this.parcela));
        } else {
            this.subscribeToSaveResponse(this.parcelaService.create(this.parcela));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IParcela>>) {
        result.subscribe((res: HttpResponse<IParcela>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCartaoById(index: number, item: ICartao) {
        return item.id;
    }

    trackPagamentoById(index: number, item: IPagamento) {
        return item.id;
    }
}
