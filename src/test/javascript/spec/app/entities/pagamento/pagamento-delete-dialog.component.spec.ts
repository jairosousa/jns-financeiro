/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { PagamentoDeleteDialogComponent } from 'app/entities/pagamento/pagamento-delete-dialog.component';
import { PagamentoService } from 'app/entities/pagamento/pagamento.service';

describe('Component Tests', () => {
    describe('Pagamento Management Delete Component', () => {
        let comp: PagamentoDeleteDialogComponent;
        let fixture: ComponentFixture<PagamentoDeleteDialogComponent>;
        let service: PagamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [PagamentoDeleteDialogComponent]
            })
                .overrideTemplate(PagamentoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PagamentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagamentoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
