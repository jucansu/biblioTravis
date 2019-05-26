import { Moment } from 'moment';
import { IBibliotecario } from 'app/shared/model/bibliotecario.model';
import { IAdministrativo } from 'app/shared/model/administrativo.model';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';

export interface INotificacionInfo {
    id?: number;
    nombre?: string;
    descripcion?: string;
    fechaInicio?: Moment;
    fechaFin?: Moment;
    bibliotecario?: IBibliotecario;
    administrativo?: IAdministrativo;
    biblioteca?: IBiblioteca;
    salaEstudio?: ISalaEstudio;
}

export class NotificacionInfo implements INotificacionInfo {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public fechaInicio?: Moment,
        public fechaFin?: Moment,
        public bibliotecario?: IBibliotecario,
        public administrativo?: IAdministrativo,
        public biblioteca?: IBiblioteca,
        public salaEstudio?: ISalaEstudio
    ) {}
}
