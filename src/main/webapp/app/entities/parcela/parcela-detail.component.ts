import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IParcela } from 'app/shared/model/parcela.model';
@Component({
    selector: 'jhi-parcela-detail',
    templateUrl: './parcela-detail.component.html'
})
export class ParcelaDetailComponent implements OnInit {
    parcela: IParcela;
    constructor(private activatedRoute: ActivatedRoute) {}
    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parcela }) => {
            this.parcela = parcela;
        });
    }
    previousState() {
        window.history.back();
    }
}
