import { IParcela } from 'app/shared/model//parcela.model';

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
    PARCELADO = 'PARCELADO'
}

export interface IPagamento {
    id?: number;
    quantidadeParcelas?: number;
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
        public formaPag?: FormaPagamento,
        public status?: Status,
        public tipoPagamento?: TipoPagamento,
        public parcelas?: IParcela[],
        public lancamentoId?: number
    ) {}
}
