/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { CartaoDeleteDialogComponent } from 'app/entities/cartao/cartao-delete-dialog.component';
import { CartaoService } from 'app/entities/cartao/cartao.service';

describe('Component Tests', () => {
    describe('Cartao Management Delete Component', () => {
        let comp: CartaoDeleteDialogComponent;
        let fixture: ComponentFixture<CartaoDeleteDialogComponent>;
        let service: CartaoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [CartaoDeleteDialogComponent]
            })
                .overrideTemplate(CartaoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CartaoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartaoService);
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
