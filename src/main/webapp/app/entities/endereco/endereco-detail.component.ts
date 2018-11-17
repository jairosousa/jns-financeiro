import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEndereco } from 'app/shared/model/endereco.model';

@Component({
    selector: 'jhi-endereco-detail',
    templateUrl: './endereco-detail.component.html'
})
export class EnderecoDetailComponent implements OnInit {
    endereco: IEndereco;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ endereco }) => {
            this.endereco = endereco;
        });
    }

    previousState() {
        window.history.back();
    }
}
