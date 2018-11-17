import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPagamento } from 'app/shared/model/pagamento.model';

@Component({
    selector: 'jhi-pagamento-detail',
    templateUrl: './pagamento-detail.component.html'
})
export class PagamentoDetailComponent implements OnInit {
    pagamento: IPagamento;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pagamento }) => {
            this.pagamento = pagamento;
        });
    }

    previousState() {
        window.history.back();
    }
}
