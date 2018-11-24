/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JnsFinanceiroTestModule } from '../../../test.module';
import { ParcelaUpdateComponent } from 'app/entities/parcela/parcela-update.component';
import { ParcelaService } from 'app/entities/parcela/parcela.service';
import { Parcela } from 'app/shared/model/parcela.model';

describe('Component Tests', () => {
    describe('Parcela Management Update Component', () => {
        let comp: ParcelaUpdateComponent;
        let fixture: ComponentFixture<ParcelaUpdateComponent>;
        let service: ParcelaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JnsFinanceiroTestModule],
                declarations: [ParcelaUpdateComponent]
            })
                .overrideTemplate(ParcelaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParcelaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParcelaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Parcela(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.parcela = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Parcela();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.parcela = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
