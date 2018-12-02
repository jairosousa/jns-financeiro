import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from './pagamento.service';
import { ILancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from 'app/entities/lancamento';

@Component({
    selector: 'jhi-pagamento-update',
    templateUrl: './pagamento-update.component.html'
})
export class PagamentoUpdateComponent implements OnInit {
    pagamento: IPagamento;
    isSaving: boolean;

    dataPrimeiroVencimentoDp: any;

    lancamentos: ILancamento[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private pagamentoService: PagamentoService,
        private lancamentoService: LancamentoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ pagamento }) => {
            this.pagamento = pagamento;
        });
        this.lancamentoService.query().subscribe(
            (res: HttpResponse<ILancamento[]>) => {
                this.lancamentos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.pagamento.id !== undefined) {
            this.subscribeToSaveResponse(this.pagamentoService.update(this.pagamento));
        } else {
            this.subscribeToSaveResponse(this.pagamentoService.create(this.pagamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPagamento>>) {
        result.subscribe((res: HttpResponse<IPagamento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLancamentoById(index: number, item: ILancamento) {
        return item.id;
    }
}
