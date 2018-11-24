import { Endereco, IEndereco } from 'app/shared/model/endereco.model';

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
    endereco?: IEndereco;
}

export class Fornecedor implements IFornecedor {
    constructor(
        public id?: number,
        public nome?: string,
        public pessoa?: Pessoa,
        public cnpj?: string,
        public cpf?: string,
        public enderecoId?: number,
        public endereco?: IEndereco
    ) {
        this.endereco = new Endereco();
    }
}
