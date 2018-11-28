import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from './categoria.service';

@Component({
    selector: 'jhi-categoria-update',
    templateUrl: './categoria-update.component.html'
})
export class CategoriaUpdateComponent implements OnInit {
    categoria: ICategoria;
    isSaving: boolean;

    currentAction: string;

    constructor(private categoriaService: CategoriaService, private activatedRoute: ActivatedRoute, private router: Router) {}

    ngOnInit() {
        this.isSaving = false;
        this.setCurrentyAction();
        this.activatedRoute.data.subscribe(({ categoria }) => {
            this.categoria = categoria;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.categoria.id !== undefined) {
            this.subscribeToSaveResponse(this.categoriaService.update(this.categoria));
        } else {
            this.subscribeToSaveResponse(this.categoriaService.create(this.categoria));
        }
    }

    // METHODOS PRIVATES

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICategoria>>) {
        result.subscribe((res: HttpResponse<ICategoria>) => this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(categoria: ICategoria) {
        this.isSaving = false;
        this.router
            .navigateByUrl('categoria', { skipLocationChange: true })
            .then(() => this.router.navigate(['categoria', categoria.id, 'edit']));
        // this.previousState();
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
