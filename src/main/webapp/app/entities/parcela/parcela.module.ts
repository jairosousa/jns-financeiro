import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JnsFinanceiroSharedModule } from 'app/shared';
import {
    ParcelaComponent,
    ParcelaDetailComponent,
    ParcelaUpdateComponent,
    ParcelaDeletePopupComponent,
    ParcelaDeleteDialogComponent,
    parcelaRoute,
    parcelaPopupRoute
} from './';

const ENTITY_STATES = [...parcelaRoute, ...parcelaPopupRoute];

@NgModule({
    imports: [JnsFinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ParcelaComponent,
        ParcelaDetailComponent,
        ParcelaUpdateComponent,
        ParcelaDeleteDialogComponent,
        ParcelaDeletePopupComponent
    ],
    entryComponents: [ParcelaComponent, ParcelaUpdateComponent, ParcelaDeleteDialogComponent, ParcelaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroParcelaModule {}
