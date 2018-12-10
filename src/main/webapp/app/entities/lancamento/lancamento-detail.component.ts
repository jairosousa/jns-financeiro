import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILancamento } from 'app/shared/model/lancamento.model';
import { IParcela } from 'app/shared/model/parcela.model';
import { ParcelaService } from 'app/entities/parcela';
import { HttpResponse } from '@angular/common/http';
import { faCheckCircle, faHandHoldingUsd } from '@fortawesome/free-solid-svg-icons';

@Component({
    selector: 'jhi-lancamento-detail',
    templateUrl: './lancamento-detail.component.html'
})
export class LancamentoDetailComponent implements OnInit {
    lancamento: ILancamento;
    parcelas: IParcela[];

    faPag = faHandHoldingUsd;

    faPay = faCheckCircle;

    constructor(private activatedRoute: ActivatedRoute, private parcelaService: ParcelaService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            this.lancamento = lancamento;
        });
        this.parcelaService.findByPagamento(this.lancamento.pagamentoId).subscribe((res: HttpResponse<IParcela[]>) => {
            this.parcelas = res.body;
        });
    }

    trackId(index: number, item: IParcela) {
        return item.id;
    }

    previousState() {
        window.history.back();
    }
}
