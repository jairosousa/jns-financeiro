export const enum Bandeira {
    AMERICAN = 'AMERICAN',
    CIELO = 'CIELO',
    ELO = 'ELO',
    MASTERCARD = 'MASTERCARD',
    VISA = 'VISA',
    OUTROS = 'OUTROS'
}

export interface ICartao {
    id?: number;
    nome?: string;
    bandeira?: Bandeira;
    numero?: number;
    logotipoContentType?: string;
    logotipo?: any;
}

export class Cartao implements ICartao {
    constructor(
        public id?: number,
        public nome?: string,
        public bandeira?: Bandeira,
        public numero?: number,
        public logotipoContentType?: string,
        public logotipo?: any
    ) {}
}
