import { Moment } from 'moment';
import { IEstudiante } from 'app/shared/model/estudiante.model';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';

export interface IReporte {
    id?: number;
    nombre?: string;
    descripcion?: string;
    fecha?: Moment;
    estudiante?: IEstudiante;
    biblioteca?: IBiblioteca;
    salaEstudio?: ISalaEstudio;
}

export class Reporte implements IReporte {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public fecha?: Moment,
        public estudiante?: IEstudiante,
        public biblioteca?: IBiblioteca,
        public salaEstudio?: ISalaEstudio
    ) {}
}
