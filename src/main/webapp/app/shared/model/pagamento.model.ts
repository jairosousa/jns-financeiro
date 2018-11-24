import { Moment } from 'moment';
import { IParcela } from 'app/shared/model//parcela.model';

export const enum Status {
    PAGO = 'PAGO',
    PENDENTE = 'PENDENTE'
}

export const enum TipoPagamento {
    AVISTA = 'AVISTA',
    PARCELADO = 'PARCELADO'
}

export interface IPagamento {
    id?: number;
    vencimento?: Moment;
    diaPagamento?: Moment;
    quantidadeParcelas?: number;
    status?: Status;
    tipoPagamento?: TipoPagamento;
    parcelas?: IParcela[];
    lancamentoId?: number;
}

export class Pagamento implements IPagamento {
    constructor(
        public id?: number,
        public vencimento?: Moment,
        public diaPagamento?: Moment,
        public quantidadeParcelas?: number,
        public status?: Status,
        public tipoPagamento?: TipoPagamento,
        public parcelas?: IParcela[],
        public lancamentoId?: number
    ) {}
}
