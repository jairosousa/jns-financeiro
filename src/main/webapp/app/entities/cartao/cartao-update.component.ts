import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { ICartao } from 'app/shared/model/cartao.model';
import { CartaoService } from './cartao.service';

@Component({
    selector: 'jhi-cartao-update',
    templateUrl: './cartao-update.component.html'
})
export class CartaoUpdateComponent implements OnInit {
    cartao: ICartao;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private cartaoService: CartaoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.cartao, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cartao.id !== undefined) {
            this.subscribeToSaveResponse(this.cartaoService.update(this.cartao));
        } else {
            this.subscribeToSaveResponse(this.cartaoService.create(this.cartao));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICartao>>) {
        result.subscribe((res: HttpResponse<ICartao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
