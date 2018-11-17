/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { LancamentoDetailComponent } from 'app/entities/lancamento/lancamento-detail.component';
import { Lancamento } from 'app/shared/model/lancamento.model';

describe('Component Tests', () => {
    describe('Lancamento Management Detail Component', () => {
        let comp: LancamentoDetailComponent;
        let fixture: ComponentFixture<LancamentoDetailComponent>;
        const route = ({ data: of({ lancamento: new Lancamento(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [LancamentoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LancamentoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LancamentoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.lancamento).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
