/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { LancamentoUpdateComponent } from 'app/entities/lancamento/lancamento-update.component';
import { LancamentoService } from 'app/entities/lancamento/lancamento.service';
import { Lancamento } from 'app/shared/model/lancamento.model';

describe('Component Tests', () => {
    describe('Lancamento Management Update Component', () => {
        let comp: LancamentoUpdateComponent;
        let fixture: ComponentFixture<LancamentoUpdateComponent>;
        let service: LancamentoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [LancamentoUpdateComponent]
            })
                .overrideTemplate(LancamentoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LancamentoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LancamentoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Lancamento(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lancamento = entity;
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
                    const entity = new Lancamento();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.lancamento = entity;
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
