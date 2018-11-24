/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { ParcelaDetailComponent } from 'app/entities/parcela/parcela-detail.component';
import { Parcela } from 'app/shared/model/parcela.model';

describe('Component Tests', () => {
    describe('Parcela Management Detail Component', () => {
        let comp: ParcelaDetailComponent;
        let fixture: ComponentFixture<ParcelaDetailComponent>;
        const route = ({ data: of({ parcela: new Parcela(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [ParcelaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParcelaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParcelaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.parcela).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
