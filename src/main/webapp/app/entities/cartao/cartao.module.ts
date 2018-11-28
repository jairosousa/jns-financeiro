import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JnsFinanceiroSharedModule } from 'app/shared';
import {
    CartaoComponent,
    CartaoDetailComponent,
    CartaoUpdateComponent,
    CartaoDeletePopupComponent,
    CartaoDeleteDialogComponent,
    cartaoRoute,
    cartaoPopupRoute
} from './';

const ENTITY_STATES = [...cartaoRoute, ...cartaoPopupRoute];

@NgModule({
    imports: [JnsFinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CartaoComponent, CartaoDetailComponent, CartaoUpdateComponent, CartaoDeleteDialogComponent, CartaoDeletePopupComponent],
    entryComponents: [CartaoComponent, CartaoUpdateComponent, CartaoDeleteDialogComponent, CartaoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroCartaoModule {}
