import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILancamento } from 'app/shared/model/lancamento.model';

@Component({
    selector: 'jhi-lancamento-detail',
    templateUrl: './lancamento-detail.component.html'
})
export class LancamentoDetailComponent implements OnInit {
    lancamento: ILancamento;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            this.lancamento = lancamento;
        });
    }

    previousState() {
        window.history.back();
    }
}
