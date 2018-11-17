import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoria } from 'app/shared/model/categoria.model';

@Component({
    selector: 'jhi-categoria-detail',
    templateUrl: './categoria-detail.component.html'
})
export class CategoriaDetailComponent implements OnInit {
    categoria: ICategoria;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ categoria }) => {
            this.categoria = categoria;
        });
    }

    previousState() {
        window.history.back();
    }
}
