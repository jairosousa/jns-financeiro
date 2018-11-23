/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { ParcelaComponent } from 'app/entities/parcela/parcela.component';
import { ParcelaService } from 'app/entities/parcela/parcela.service';
import { Parcela } from 'app/shared/model/parcela.model';

describe('Component Tests', () => {
    describe('Parcela Management Component', () => {
        let comp: ParcelaComponent;
        let fixture: ComponentFixture<ParcelaComponent>;
        let service: ParcelaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [ParcelaComponent],
                providers: []
            })
                .overrideTemplate(ParcelaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParcelaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParcelaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Parcela(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.parcelas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
