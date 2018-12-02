import { Moment } from 'moment';

export const enum Status {
    PAGO = 'PAGO',
    PENDENTE = 'PENDENTE',
    CANCELADO = 'CANCELADO'
}

export interface IParcela {
    id?: number;
    dataVencimento?: Moment;
    dataPagamento?: Moment;
    numero?: number;
    valor?: number;
    juros?: number;
    total?: number;
    status?: Status;
    cartaoNome?: string;
    cartaoId?: number;
    pagamentoFormaPag?: string;
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
        public status?: Status,
        public cartaoNome?: string,
        public cartaoId?: number,
        public pagamentoFormaPag?: string,
        public pagamentoId?: number
    ) {}
}
