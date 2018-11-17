/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { PagamentoUpdateComponent } from 'app/entities/pagamento/pagamento-update.component';
import { PagamentoService } from 'app/entities/pagamento/pagamento.service';
import { Pagamento } from 'app/shared/model/pagamento.model';

describe('Component Tests', () => {
    describe('Pagamento Management Update Component', () => {
        let comp: PagamentoUpdateComponent;
        let fixture: ComponentFixture<PagamentoUpdateComponent>;
        let service: PagamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [PagamentoUpdateComponent]
            })
                .overrideTemplate(PagamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PagamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PagamentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pagamento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pagamento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Pagamento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.pagamento = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
