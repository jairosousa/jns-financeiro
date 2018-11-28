import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICartao } from 'app/shared/model/cartao.model';

@Component({
    selector: 'jhi-cartao-detail',
    templateUrl: './cartao-detail.component.html'
})
export class CartaoDetailComponent implements OnInit {
    cartao: ICartao;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cartao }) => {
            this.cartao = cartao;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
