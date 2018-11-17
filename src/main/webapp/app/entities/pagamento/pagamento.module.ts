import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JnsFinanceiroSharedModule } from 'app/shared';
import {
    PagamentoComponent,
    PagamentoDetailComponent,
    PagamentoUpdateComponent,
    PagamentoDeletePopupComponent,
    PagamentoDeleteDialogComponent,
    pagamentoRoute,
    pagamentoPopupRoute
} from './';

const ENTITY_STATES = [...pagamentoRoute, ...pagamentoPopupRoute];

@NgModule({
    imports: [JnsFinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PagamentoComponent,
        PagamentoDetailComponent,
        PagamentoUpdateComponent,
        PagamentoDeleteDialogComponent,
        PagamentoDeletePopupComponent
    ],
    entryComponents: [PagamentoComponent, PagamentoUpdateComponent, PagamentoDeleteDialogComponent, PagamentoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroPagamentoModule {}
