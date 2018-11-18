import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { Endereco, IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';
import { ILancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from 'app/entities/lancamento';

@Component({
    selector: 'jhi-fornecedor-update',
    templateUrl: './fornecedor-update.component.html'
})
export class FornecedorUpdateComponent implements OnInit {
    fornecedor: IFornecedor;
    endereco: IEndereco;
    isSaving: boolean;

    enderecos: IEndereco[];

    lancamentos: ILancamento[];

    currentAction: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private fornecedorService: FornecedorService,
        private enderecoService: EnderecoService,
        private lancamentoService: LancamentoService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.endereco = {};
        this.isSaving = false;
        this.setCurrentyAction();
        this.activatedRoute.data.subscribe(({ fornecedor }) => {
            this.fornecedor = fornecedor;
        });

        this.enderecoService.query({ filter: 'fornecedor-is-null' }).subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                if (!this.fornecedor.enderecoId) {
                } else {
                    this.enderecoService.find(this.fornecedor.enderecoId).subscribe(
                        (subRes: HttpResponse<IEndereco>) => {
                            this.endereco = subRes.body;
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.fornecedor.endereco = this.endereco;
        if (this.fornecedor.id !== undefined) {
            this.subscribeToSaveResponse(this.fornecedorService.update(this.fornecedor));
        } else {
            this.subscribeToSaveResponse(this.fornecedorService.create(this.fornecedor));
        }
    }

    // METHODOS PRIVATES

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>) {
        result.subscribe((res: HttpResponse<IFornecedor>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(fornecedor: IFornecedor) {
        this.isSaving = false;
        this.router
            .navigateByUrl('fornecedor', { skipLocationChange: true })
            .then(() => this.router.navigate(['fornecedor', fornecedor.id, 'edit']));
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackEnderecoById(index: number, item: IEndereco) {
        return item.id;
    }

    trackLancamentoById(index: number, item: ILancamento) {
        return item.id;
    }

    private setCurrentyAction() {
        if (this.activatedRoute.snapshot.url[1].path === 'new') {
            this.currentAction = 'new';
        } else {
            this.currentAction = 'edit';
        }
    }
}
