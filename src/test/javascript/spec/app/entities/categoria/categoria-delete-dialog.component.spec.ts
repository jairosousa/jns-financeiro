/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { CategoriaDeleteDialogComponent } from 'app/entities/categoria/categoria-delete-dialog.component';
import { CategoriaService } from 'app/entities/categoria/categoria.service';

describe('Component Tests', () => {
    describe('Categoria Management Delete Component', () => {
        let comp: CategoriaDeleteDialogComponent;
        let fixture: ComponentFixture<CategoriaDeleteDialogComponent>;
        let service: CategoriaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [CategoriaDeleteDialogComponent]
            })
                .overrideTemplate(CategoriaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoriaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategoriaService);
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
