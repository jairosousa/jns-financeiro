export const enum Pessoa {
    FISICA = 'FISICA',
    JURIDICA = 'JURIDICA'
}

export interface IFornecedor {
    id?: number;
    nome?: string;
    razaoSocial?: string;
    telefoneFixo?: string;
    telefoneCel?: string;
    pessoa?: Pessoa;
    cnpj?: string;
    cpf?: string;
    enderecoId?: number;
}

export class Fornecedor implements IFornecedor {
    constructor(
        public id?: number,
        public nome?: string,
        public razaoSocial?: string,
        public telefoneFixo?: string,
        public telefoneCel?: string,
        public pessoa?: Pessoa,
        public cnpj?: string,
        public cpf?: string,
        public enderecoId?: number
    ) {}
}
