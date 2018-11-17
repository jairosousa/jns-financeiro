import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ILancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';
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

    fornecedors: IFornecedor[];

    categorias: ICategoria[];
    dataDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private lancamentoService: LancamentoService,
        private fornecedorService: FornecedorService,
        private categoriaService: CategoriaService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            this.lancamento = lancamento;
        });
        this.fornecedorService.query({ filter: 'lancamento(nome)-is-null' }).subscribe(
            (res: HttpResponse<IFornecedor[]>) => {
                if (!this.lancamento.fornecedorId) {
                    this.fornecedors = res.body;
                } else {
                    this.fornecedorService.find(this.lancamento.fornecedorId).subscribe(
                        (subRes: HttpResponse<IFornecedor>) => {
                            this.fornecedors = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.categoriaService.query({ filter: 'lancamento-is-null' }).subscribe(
            (res: HttpResponse<ICategoria[]>) => {
                if (!this.lancamento.categoriaId) {
                    this.categorias = res.body;
                } else {
                    this.categoriaService.find(this.lancamento.categoriaId).subscribe(
                        (subRes: HttpResponse<ICategoria>) => {
                            this.categorias = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
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

    trackFornecedorById(index: number, item: IFornecedor) {
        return item.id;
    }

    trackCategoriaById(index: number, item: ICategoria) {
        return item.id;
    }
}
