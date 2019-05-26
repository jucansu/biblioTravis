import { Moment } from 'moment';
import { IEstudiante } from 'app/shared/model/estudiante.model';

export interface IAviso {
    id?: number;
    nombre?: string;
    descripcion?: string;
    fecha?: Moment;
    estudiante?: IEstudiante;
}

export class Aviso implements IAviso {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public fecha?: Moment,
        public estudiante?: IEstudiante
    ) {}
}
