export interface IEndereco {
    id?: number;
    cep?: string;
    logradouro?: string;
    numero?: string;
    complemento?: string;
    bairro?: string;
    cidade?: string;
    uf?: string;
}

export class Endereco implements IEndereco {
    constructor(
        public id?: number,
        public cep?: string,
        public logradouro?: string,
        public numero?: string,
        public complemento?: string,
        public bairro?: string,
        public cidade?: string,
        public uf?: string
    ) {}
}
