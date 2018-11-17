import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';

@Component({
    selector: 'jhi-endereco-delete-dialog',
    templateUrl: './endereco-delete-dialog.component.html'
})
export class EnderecoDeleteDialogComponent {
    endereco: IEndereco;

    constructor(private enderecoService: EnderecoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enderecoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'enderecoListModification',
                content: 'Deleted an endereco'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-endereco-delete-popup',
    template: ''
})
export class EnderecoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ endereco }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EnderecoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.endereco = endereco;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
