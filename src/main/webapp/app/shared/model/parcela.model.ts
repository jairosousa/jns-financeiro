import { Moment } from 'moment';

export const enum FormaPagamento {
    DINHEIRO = 'DINHEIRO',
    CREDITO = 'CARTAO',
    DEBITO = 'DEBITO'
}

export const enum Status {
    PAGO = 'PAGO',
    PENDENTE = 'PENDENTE'
}

export interface IParcela {
    id?: number;
    dataVencimento?: Moment;
    dataPagamento?: Moment;
    numero?: number;
    valor?: number;
    juros?: number;
    total?: number;
    forma?: FormaPagamento;
    status?: Status;
    cartaoNome?: string;
    cartaoId?: number;
    pagamentoId?: number;
}

export class Parcela implements IParcela {
    constructor(
        public id?: number,
        public dataVencimento?: Moment,
        public dataPagamento?: Moment,
        public numero?: number,
        public valor?: number,
        public juros?: number,
        public total?: number,
        public forma?: FormaPagamento,
        public status?: Status,
        public cartaoNome?: string,
        public cartaoId?: number,
        public pagamentoId?: number
    ) {}
}
