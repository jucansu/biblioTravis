import { Moment } from 'moment';
import { IBibliotecario } from 'app/shared/model/bibliotecario.model';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';

export interface ICorreccion {
    id?: number;
    nombre?: string;
    descripcion?: string;
    fecha?: Moment;
    bibliotecario?: IBibliotecario;
    biblioteca?: IBiblioteca;
}

export class Correccion implements ICorreccion {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public fecha?: Moment,
        public bibliotecario?: IBibliotecario,
        public biblioteca?: IBiblioteca
    ) {}
}
