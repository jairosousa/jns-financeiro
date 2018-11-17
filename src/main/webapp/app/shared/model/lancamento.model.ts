import { Moment } from 'moment';
import { IPagamento } from 'app/shared/model//pagamento.model';

export const enum Tipo {
    DESPESA = 'DESPESA',
    RECEITA = 'RECEITA'
}

export const enum TipoPagamento {
    AVISTA = 'AVISTA',
    PARCELADO = 'PARCELADO'
}

export interface ILancamento {
    id?: number;
    data?: Moment;
    nome?: string;
    descricao?: string;
    valor?: number;
    tipo?: Tipo;
    tipoPagamento?: TipoPagamento;
    fornecedorNome?: string;
    fornecedorId?: number;
    categoriaNome?: string;
    categoriaId?: number;
    pagamentos?: IPagamento[];
}

export class Lancamento implements ILancamento {
    constructor(
        public id?: number,
        public data?: Moment,
        public nome?: string,
        public descricao?: string,
        public valor?: number,
        public tipo?: Tipo,
        public tipoPagamento?: TipoPagamento,
        public fornecedorNome?: string,
        public fornecedorId?: number,
        public categoriaNome?: string,
        public categoriaId?: number,
        public pagamentos?: IPagamento[]
    ) {}
}
