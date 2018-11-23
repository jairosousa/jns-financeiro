/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { ParcelaDeleteDialogComponent } from 'app/entities/parcela/parcela-delete-dialog.component';
import { ParcelaService } from 'app/entities/parcela/parcela.service';

describe('Component Tests', () => {
    describe('Parcela Management Delete Component', () => {
        let comp: ParcelaDeleteDialogComponent;
        let fixture: ComponentFixture<ParcelaDeleteDialogComponent>;
        let service: ParcelaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [ParcelaDeleteDialogComponent]
            })
                .overrideTemplate(ParcelaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParcelaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParcelaService);
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
