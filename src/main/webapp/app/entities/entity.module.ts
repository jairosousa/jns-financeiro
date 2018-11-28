import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JnsFinanceiroLancamentoModule } from './lancamento/lancamento.module';
import { JnsFinanceiroFornecedorModule } from './fornecedor/fornecedor.module';
import { JnsFinanceiroCategoriaModule } from './categoria/categoria.module';
import { JnsFinanceiroEnderecoModule } from './endereco/endereco.module';
import { JnsFinanceiroPagamentoModule } from './pagamento/pagamento.module';
import { JnsFinanceiroParcelaModule } from 'app/entities/parcela/parcela.module';
import { JnsFinanceiroParcelaModule } from './parcela/parcela.module';
import { JnsFinanceiroCartaoModule } from './cartao/cartao.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JnsFinanceiroLancamentoModule,
        JnsFinanceiroFornecedorModule,
        JnsFinanceiroCategoriaModule,
        JnsFinanceiroEnderecoModule,
        JnsFinanceiroPagamentoModule,
        JnsFinanceiroParcelaModule,
        JnsFinanceiroCartaoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JnsFinanceiroEntityModule {}
