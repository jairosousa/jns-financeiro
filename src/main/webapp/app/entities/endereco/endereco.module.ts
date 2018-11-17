import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JnsFinanceiroSharedModule } from 'app/shared';
import {
    EnderecoComponent,
    EnderecoDetailComponent,
    EnderecoUpdateComponent,
    EnderecoDeletePopupComponent,
    EnderecoDeleteDialogComponent,
    enderecoRoute,
    enderecoPopupRoute
} from './';

const ENTITY_STATES = [...enderecoRoute, ...enderecoPopupRoute];

@NgModule({
    imports: [JnsFinanceiroSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EnderecoComponent,
        EnderecoDetailComponent,
        EnderecoUpdateComponent,
        EnderecoDeleteDialogComponent,
        EnderecoDeletePopupComponent
    ],
    entryComponents: [EnderecoComponent, EnderecoUpdateComponent, EnderecoDeleteDialogComponent, EnderecoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroEnderecoModule {}
