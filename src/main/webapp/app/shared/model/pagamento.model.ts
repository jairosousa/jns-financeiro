import { IParcela } from 'app/shared/model//parcela.model';
import { Moment } from 'moment';

export const enum FormaPagamento {
    DINHEIRO = 'DINHEIRO',
    CREDITO = 'CREDITO',
    DEBITO = 'DEBITO'
}

export const enum Status {
    PAGO = 'PAGO',
    PENDENTE = 'PENDENTE'
}

export const enum TipoPagamento {
    AVISTA = 'AVISTA',
    PARCELADO = 'PARCELADO',
    CANCELADO = 'CANCELADO'
}

export interface IPagamento {
    id?: number;
    quantidadeParcelas?: number;
    dataPrimeiroVencimento?: Moment;
    formaPag?: FormaPagamento;
    status?: Status;
    tipoPagamento?: TipoPagamento;
    parcelas?: IParcela[];
    lancamentoId?: number;
}

export class Pagamento implements IPagamento {
    constructor(
        public id?: number,
        public quantidadeParcelas?: number,
        public dataPrimeiroVencimento?: Moment,
        public formaPag?: FormaPagamento,
        public status?: Status,
        public tipoPagamento?: TipoPagamento,
        public parcelas?: IParcela[],
        public lancamentoId?: number
    ) {}
}
