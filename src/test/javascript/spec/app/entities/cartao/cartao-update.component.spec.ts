/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { CartaoUpdateComponent } from 'app/entities/cartao/cartao-update.component';
import { CartaoService } from 'app/entities/cartao/cartao.service';
import { Cartao } from 'app/shared/model/cartao.model';

describe('Component Tests', () => {
    describe('Cartao Management Update Component', () => {
        let comp: CartaoUpdateComponent;
        let fixture: ComponentFixture<CartaoUpdateComponent>;
        let service: CartaoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [CartaoUpdateComponent]
            })
                .overrideTemplate(CartaoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CartaoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartaoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cartao(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cartao = entity;
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
                    const entity = new Cartao();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cartao = entity;
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
