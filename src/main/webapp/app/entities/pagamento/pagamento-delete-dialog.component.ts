import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPagamento } from 'app/shared/model/pagamento.model';
import { PagamentoService } from './pagamento.service';

@Component({
    selector: 'jhi-pagamento-delete-dialog',
    templateUrl: './pagamento-delete-dialog.component.html'
})
export class PagamentoDeleteDialogComponent {
    pagamento: IPagamento;

    constructor(private pagamentoService: PagamentoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pagamentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pagamentoListModification',
                content: 'Deleted an pagamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pagamento-delete-popup',
    template: ''
})
export class PagamentoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pagamento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PagamentoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pagamento = pagamento;
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
