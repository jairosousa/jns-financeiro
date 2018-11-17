export interface ICategoria {
    id?: number;
    nome?: string;
    descricao?: string;
}

export class Categoria implements ICategoria {
    constructor(public id?: number, public nome?: string, public descricao?: string) {}
}
