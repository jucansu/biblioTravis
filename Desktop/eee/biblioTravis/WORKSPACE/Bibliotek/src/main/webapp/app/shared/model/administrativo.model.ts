import { INotificacionInfo } from 'app/shared/model/notificacion-info.model';
import { ISalaEstudio } from 'app/shared/model/sala-estudio.model';

export interface IAdministrativo {
    id?: number;
    notificacionInfos?: INotificacionInfo[];
    salaEstudios?: ISalaEstudio[];
}

export class Administrativo implements IAdministrativo {
    constructor(public id?: number, public notificacionInfos?: INotificacionInfo[], public salaEstudios?: ISalaEstudio[]) {}
}
