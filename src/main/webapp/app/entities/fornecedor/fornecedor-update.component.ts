import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFornecedor, Pessoa } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';

@Component({
    selector: 'jhi-fornecedor-update',
    templateUrl: './fornecedor-update.component.html'
})
export class FornecedorUpdateComponent implements OnInit {
    fornecedor: IFornecedor;
    endereco: IEndereco;
    isSaving: boolean;

    enderecos: IEndereco[];

    currentAction: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private fornecedorService: FornecedorService,
        private enderecoService: EnderecoService,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {
        this.endereco = {};
        this.isSaving = false;
        this.setCurrentyAction();
        this.activatedRoute.data.subscribe(({ fornecedor }) => {
            this.fornecedor = fornecedor;
            this.endereco = this.fornecedor.endereco;
        });
        this.enderecoService.query({ filter: 'fornecedor-is-null' }).subscribe(
            (res: HttpResponse<IEndereco[]>) => {
                if (!this.fornecedor.enderecoId) {
                    this.endereco = {};
                    this.fornecedor.pessoa = Pessoa.FISICA;
                }
                // else {
                //     this.enderecoService.find(this.fornecedor.enderecoId).subscribe(
                //         (subRes: HttpResponse<IEndereco>) => {
                //             this.endereco = subRes.body;
                //         },
                //         (subRes: HttpErrorResponse) => this.onError(subRes.message)
                //     );
                // }
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

    findEnderecoBy(cep: string) {
        this.enderecoService.findEndereco(this.endereco.cep).subscribe(
            (subRes: HttpResponse<IEndereco>) => {
                this.endereco = subRes.body;
            },
            (subRes: HttpErrorResponse) => {
                this.endereco = {};
                console.log(subRes);
                this.onErrorCep(subRes.message);
            }
        );
    }

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

    private onErrorCep(errorMessage: string) {
        this.jhiAlertService.error('Cep Não Encontrado', null, null);
    }

    private setCurrentyAction() {
        if (this.activatedRoute.snapshot.url[1].path === 'new') {
            this.currentAction = 'new';
        } else {
            this.currentAction = 'edit';
        }
    }
}
