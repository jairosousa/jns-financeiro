import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ILancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';
import { IPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from 'app/entities/pagamento';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';

@Component({
    selector: 'jhi-lancamento-update',
    templateUrl: './lancamento-update.component.html'
})
export class LancamentoUpdateComponent implements OnInit {
    lancamento: ILancamento;
    isSaving: boolean;

    pagamentos: IPagamento[];

    fornecedors: IFornecedor[];

    categorias: ICategoria[];
    dataDp: any;

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
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            this.lancamento = lancamento;
        });
        this.pagamentoService.query({ filter: 'lancamento-is-null' }).subscribe(
            (res: HttpResponse<IPagamento[]>) => {
                if (!this.lancamento.pagamentoId) {
                    this.pagamentos = res.body;
                } else {
                    this.pagamentoService.find(this.lancamento.pagamentoId).subscribe(
                        (subRes: HttpResponse<IPagamento>) => {
                            this.pagamentos = [subRes.body].concat(res.body);
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
            this.subscribeToSaveResponse(this.lancamentoService.update(this.lancamento));
        } else {
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
}
