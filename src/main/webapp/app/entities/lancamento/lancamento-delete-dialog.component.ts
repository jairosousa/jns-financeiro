import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILancamento } from 'app/shared/model/lancamento.model';
import { LancamentoService } from './lancamento.service';

@Component({
    selector: 'jhi-lancamento-delete-dialog',
    templateUrl: './lancamento-delete-dialog.component.html'
})
export class LancamentoDeleteDialogComponent {
    lancamento: ILancamento;

    constructor(private lancamentoService: LancamentoService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lancamentoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'lancamentoListModification',
                content: 'Deleted an lancamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lancamento-delete-popup',
    template: ''
})
export class LancamentoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lancamento }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(LancamentoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.lancamento = lancamento;
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
