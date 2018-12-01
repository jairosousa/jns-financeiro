import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
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

    currentAction: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private cartaoService: CartaoService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.setCurrentyAction();
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
        result.subscribe((res: HttpResponse<ICartao>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(cartao: ICartao) {
        this.isSaving = false;
        this.router
            .navigateByUrl('categoria', { skipLocationChange: true })
            .then(() => this.router.navigate(['cartao', cartao.id, 'edit']));
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private setCurrentyAction() {
        if (this.activatedRoute.snapshot.url[1].path === 'new') {
            this.currentAction = 'new';
        } else {
            this.currentAction = 'edit';
        }
    }
}
