import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { HasAnyAuthorityDirective, JhiLoginModalComponent, JnsFinanceiroSharedCommonModule, JnsFinanceiroSharedLibsModule } from './';
import { BreadCrumbComponent } from './components/bread-crumb/bread-crumb.component';
import { RouterModule } from '@angular/router';
import { NgbDateCustomParserFormatter } from 'app/shared/util/NgbDateCustomParserFormatter';
import { CnpjPipe } from './pipes/cnpj.pipe';
import { CpfPipe } from './pipes/cpf.pipe';
import { CpfDirective } from './diretivas/cpf.directive';
import { CnpjDirective } from './diretivas/cnpj.directive';
import { MaskDirective } from './diretivas/mask.directive';
import { TelefonePipe } from './pipes/telefone.pipe';

@NgModule({
    imports: [JnsFinanceiroSharedLibsModule, JnsFinanceiroSharedCommonModule, RouterModule],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        BreadCrumbComponent,
        CnpjPipe,
        CpfPipe,
        CpfDirective,
        CnpjDirective,
        MaskDirective,
        TelefonePipe
    ],
    providers: [
        { provide: NgbDateAdapter, useClass: NgbDateMomentAdapter },
        { provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter }
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        JnsFinanceiroSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        BreadCrumbComponent,
        RouterModule,
        CnpjPipe,
        CpfPipe,
        CpfDirective,
        CnpjDirective,
        MaskDirective,
        TelefonePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroSharedModule {
    static forRoot() {
        return {
            ngModule: JnsFinanceiroSharedModule
        };
    }
}
