/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { CartaoDetailComponent } from 'app/entities/cartao/cartao-detail.component';
import { Cartao } from 'app/shared/model/cartao.model';

describe('Component Tests', () => {
    describe('Cartao Management Detail Component', () => {
        let comp: CartaoDetailComponent;
        let fixture: ComponentFixture<CartaoDetailComponent>;
        const route = ({ data: of({ cartao: new Cartao(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [CartaoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CartaoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CartaoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cartao).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
