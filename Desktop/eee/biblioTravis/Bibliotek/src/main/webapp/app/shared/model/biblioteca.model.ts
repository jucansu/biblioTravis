import { IEstudiante } from 'app/shared/model/estudiante.model';
import { ICorreccion } from 'app/shared/model/correccion.model';
import { IReporte } from 'app/shared/model/reporte.model';
import { IBibliotecario } from 'app/shared/model/bibliotecario.model';
import { INotificacionInfo } from 'app/shared/model/notificacion-info.model';

export const enum TipoZona {
    NERVION = 'NERVION',
    HELIOPOLIS = 'HELIOPOLIS',
    CARTUJA = 'CARTUJA',
    LOS_REMEDIOS = 'LOS_REMEDIOS',
    MONTEQUINTO = 'MONTEQUINTO',
    CENTRO = 'CENTRO'
}

export interface IBiblioteca {
    id?: number;
    nombre?: string;
    descripcion?: string;
    valoracion?: number;
    plazasTotales?: number;
    plazasOcupadas?: number;
    zona?: TipoZona;
    numEnchufes?: number;
    salas?: number;
    estudiante?: IEstudiante;
    correccions?: ICorreccion[];
    reportes?: IReporte[];
    bibliotecarios?: IBibliotecario[];
    notificacionInfos?: INotificacionInfo[];
    plazasDisp?: number;
    foto?: String;
    horario?: String;
    direccion?: String;
    fotoDetalle?: String;
    urlDireccion?: string;
    numVotos?: number;
}

export class Biblioteca implements IBiblioteca {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public valoracion?: number,
        public plazasTotales?: number,
        public plazasDisponibles?: number,
        public zona?: TipoZona,
        public numEnchufes?: number,
        public salas?: number,
        public estudiante?: IEstudiante,
        public correccions?: ICorreccion[],
        public reportes?: IReporte[],
        public bibliotecarios?: IBibliotecario[],
        public notificacionInfos?: INotificacionInfo[],
        public foto?: String,
        public horario?: String,
        public direccion?: String,
        public fotoDetalle?: String,
        public urlDireccion?: string,
        public numVotos?: number
    ) {}
}
