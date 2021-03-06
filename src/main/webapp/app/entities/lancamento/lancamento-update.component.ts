import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILancamento, Tipo } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';
import { FormaPagamento, IPagamento, Status, TipoPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from 'app/entities/pagamento';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import moment = require('moment');

@Component({
    selector: 'jhi-lancamento-update',
    templateUrl: './lancamento-update.component.html'
})
export class LancamentoUpdateComponent implements OnInit {
    lancamento: ILancamento;
    pagamento: IPagamento;
    isSaving: boolean;

    // pagamentos: IPagamento[];

    fornecedors: IFornecedor[];

    categorias: ICategoria[];
    dataDp: any;
    dataPrimeiroVencimentoDp: any;
    dataVencimentoDp: any;

    currentAction: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private lancamentoService: LancamentoService,
        private pagamentoService: PagamentoService,
        private fornecedorService: FornecedorService,
        private categoriaService: CategoriaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.setCurrentyAction();
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            this.lancamento = lancamento;
        });
        this.pagamentoService.query({ filter: 'lancamento-is-null' }).subscribe(
            (res: HttpResponse<IPagamento[]>) => {
                if (!this.lancamento.pagamentoId) {
                    this.lancamento.tipo = Tipo.DESPESA;
                    this.lancamento.data = moment();
                    this.pagamento = {};
                    this.pagamento.tipoPagamento = TipoPagamento.AVISTA;
                    this.pagamento.formaPag = FormaPagamento.DINHEIRO;
                    this.pagamento.quantidadeParcelas = 1;
                    this.pagamento.dataPrimeiroVencimento = moment();
                    this.pagamento.status = Status.PENDENTE;
                } else {
                    this.pagamentoService.find(this.lancamento.pagamentoId).subscribe(
                        (subRes: HttpResponse<IPagamento>) => {
                            this.pagamento = subRes.body;
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.fornecedorService.query().subscribe(
            (res: HttpResponse<IFornecedor[]>) => {
                this.fornecedors = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoriaService.query().subscribe(
            (res: HttpResponse<ICategoria[]>) => {
                this.categorias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lancamento.id !== undefined) {
            this.lancamento.pagamentoId = this.pagamento.id;
            this.subscribeToSaveResponse(this.lancamentoService.update(this.lancamento));
        } else {
            this.lancamento.pagamento = this.pagamento;
            this.subscribeToSaveResponse(this.lancamentoService.create(this.lancamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILancamento>>) {
        result.subscribe((res: HttpResponse<ILancamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPagamentoById(index: number, item: IPagamento) {
        return item.id;
    }

    trackFornecedorById(index: number, item: IFornecedor) {
        return item.id;
    }

    trackCategoriaById(index: number, item: ICategoria) {
        return item.id;
    }

    private setCurrentyAction() {
        if (this.activatedRoute.snapshot.url[1].path === 'new') {
            this.currentAction = 'new';
        } else {
            this.currentAction = 'edit';
        }
    }
}
