import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CurrencyMaskModule } from 'ng2-currency-mask';

import { JnsFinanceiroSharedModule } from 'app/shared';
import {
    LancamentoComponent,
    LancamentoDetailComponent,
    LancamentoUpdateComponent,
    LancamentoDeletePopupComponent,
    LancamentoDeleteDialogComponent,
    lancamentoRoute,
    lancamentoPopupRoute
} from './';

const ENTITY_STATES = [...lancamentoRoute, ...lancamentoPopupRoute];

@NgModule({
    imports: [JnsFinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES), CurrencyMaskModule],
    declarations: [
        LancamentoComponent,
        LancamentoDetailComponent,
        LancamentoUpdateComponent,
        LancamentoDeleteDialogComponent,
        LancamentoDeletePopupComponent
    ],
    entryComponents: [LancamentoComponent, LancamentoUpdateComponent, LancamentoDeleteDialogComponent, LancamentoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroLancamentoModule {}
