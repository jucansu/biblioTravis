import { IAdministrativo } from 'app/shared/model/administrativo.model';
import { INotificacionInfo } from 'app/shared/model/notificacion-info.model';
import { IReporte } from 'app/shared/model/reporte.model';

export const enum TipoZona {
    NERVION = 'NERVION',
    HELIOPOLIS = 'HELIOPOLIS',
    CARTUJA = 'CARTUJA',
    LOS_REMEDIOS = 'LOS_REMEDIOS',
    MONTEQUINTO = 'MONTEQUINTO',
    CENTRO = 'CENTRO'
}

export interface ISalaEstudio {
    id?: number;
    nombre?: string;
    descripcion?: string;
    valoracion?: string;
    plazasTotales?: number;
    zona?: TipoZona;
    numEnchufes?: number;
    hablar?: boolean;
    administrativo?: IAdministrativo;
    notificacionInfos?: INotificacionInfo[];
    reportes?: IReporte[];
}

export class SalaEstudio implements ISalaEstudio {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public valoracion?: string,
        public plazasTotales?: number,
        public zona?: TipoZona,
        public numEnchufes?: number,
        public hablar?: boolean,
        public administrativo?: IAdministrativo,
        public notificacionInfos?: INotificacionInfo[],
        public reportes?: IReporte[]
    ) {
        this.hablar = this.hablar || false;
    }
}
