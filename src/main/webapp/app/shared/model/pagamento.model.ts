import { Moment } from 'moment';

export const enum FormaPagamento {
    DINHEIRO = 'DINHEIRO',
    CARTAO = 'CARTAO',
    DEBITO = 'DEBITO'
}

export const enum Status {
    PAGO = 'PAGO',
    PENDENTE = 'PENDENTE'
}

export interface IPagamento {
    id?: number;
    vencimento?: Moment;
    diaPagamento?: Moment;
    forma?: FormaPagamento;
    juros?: number;
    quantidadeParcelas?: number;
    status?: Status;
    lancamentoId?: number;
}

export class Pagamento implements IPagamento {
    constructor(
        public id?: number,
        public vencimento?: Moment,
        public diaPagamento?: Moment,
        public forma?: FormaPagamento,
        public juros?: number,
        public quantidadeParcelas?: number,
        public status?: Status,
        public lancamentoId?: number
    ) {}
}
