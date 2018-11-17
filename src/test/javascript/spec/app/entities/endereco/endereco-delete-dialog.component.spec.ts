/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { EnderecoDeleteDialogComponent } from 'app/entities/endereco/endereco-delete-dialog.component';
import { EnderecoService } from 'app/entities/endereco/endereco.service';

describe('Component Tests', () => {
    describe('Endereco Management Delete Component', () => {
        let comp: EnderecoDeleteDialogComponent;
        let fixture: ComponentFixture<EnderecoDeleteDialogComponent>;
        let service: EnderecoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [EnderecoDeleteDialogComponent]
            })
                .overrideTemplate(EnderecoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnderecoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnderecoService);
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
