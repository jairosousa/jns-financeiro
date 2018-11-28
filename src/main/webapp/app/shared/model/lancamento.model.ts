import { Moment } from 'moment';

export const enum Tipo {
    DESPESA = 'DESPESA',
    RECEITA = 'RECEITA'
}

export interface ILancamento {
    id?: number;
    data?: Moment;
    nome?: string;
    descricao?: string;
    valor?: number;
    tipo?: Tipo;
    pagamentoTipoPagamento?: string;
    pagamentoId?: number;
    fornecedorNome?: string;
    fornecedorId?: number;
    categoriaNome?: string;
    categoriaId?: number;
}

export class Lancamento implements ILancamento {
    constructor(
        public id?: number,
        public data?: Moment,
        public nome?: string,
        public descricao?: string,
        public valor?: number,
        public tipo?: Tipo,
        public pagamentoTipoPagamento?: string,
        public pagamentoId?: number,
        public fornecedorNome?: string,
        public fornecedorId?: number,
        public categoriaNome?: string,
        public categoriaId?: number
    ) {}
}
