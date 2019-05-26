export interface IActor {
    id?: number;
    nombre?: string;
    apellidos?: string;
    correo?: string;
    uvus?: string;
    telefono?: string;
}

export class Actor implements IActor {
    constructor(
        public id?: number,
        public nombre?: string,
        public apellidos?: string,
        public correo?: string,
        public uvus?: string,
        public telefono?: string
    ) {}
}
