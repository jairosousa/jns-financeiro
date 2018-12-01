import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiAlertService, JhiDataUtils, JhiEventManager } from 'ng-jhipster';

import { ICartao } from 'app/shared/model/cartao.model';
import { Principal } from 'app/core';
import { CartaoService } from './cartao.service';
import { faCreditCard } from '@fortawesome/free-solid-svg-icons';

@Component({
    selector: 'jhi-cartao',
    templateUrl: './cartao.component.html'
})
export class CartaoComponent implements OnInit, OnDestroy {
    cartaos: ICartao[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    faCard = faCreditCard;

    constructor(
        private cartaoService: CartaoService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.cartaoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<ICartao[]>) => (this.cartaos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.cartaoService.query().subscribe(
            (res: HttpResponse<ICartao[]>) => {
                this.cartaos = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCartaos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICartao) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInCartaos() {
        this.eventSubscriber = this.eventManager.subscribe('cartaoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
