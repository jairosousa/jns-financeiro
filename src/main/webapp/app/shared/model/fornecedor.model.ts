export const enum Pessoa {
    FISICA = 'FISICA',
    JURIDICA = 'JURIDICA'
}

export interface IFornecedor {
    id?: number;
    nome?: string;
    pessoa?: Pessoa;
    cnpj?: string;
    cpf?: string;
    enderecoId?: number;
}

export class Fornecedor implements IFornecedor {
    constructor(
        public id?: number,
        public nome?: string,
        public pessoa?: Pessoa,
        public cnpj?: string,
        public cpf?: string,
        public enderecoId?: number
    ) {}
}
