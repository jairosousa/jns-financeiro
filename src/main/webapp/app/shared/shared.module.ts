import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { NgbDateAdapter } from '@ng-bootstrap/ng-bootstrap';

import { NgbDateMomentAdapter } from './util/datepicker-adapter';
import { JnsFinanceiroSharedLibsModule, JnsFinanceiroSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';
import { BreadCrumbComponent } from './components/bread-crumb/bread-crumb.component';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [JnsFinanceiroSharedLibsModule, JnsFinanceiroSharedCommonModule, RouterModule],
    declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective, BreadCrumbComponent],
    providers: [{ provide: NgbDateAdapter, useClass: NgbDateMomentAdapter }],
    entryComponents: [JhiLoginModalComponent],
    exports: [JnsFinanceiroSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective, BreadCrumbComponent, RouterModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroSharedModule {
    static forRoot() {
        return {
            ngModule: JnsFinanceiroSharedModule
        };
    }
}
