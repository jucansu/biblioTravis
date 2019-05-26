import { ICorreccion } from 'app/shared/model/correccion.model';
import { INotificacionInfo } from 'app/shared/model/notificacion-info.model';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';

export interface IBibliotecario {
    id?: number;
    correccions?: ICorreccion[];
    notificacionInfos?: INotificacionInfo[];
    biblioteca?: IBiblioteca;
}

export class Bibliotecario implements IBibliotecario {
    constructor(
        public id?: number,
        public correccions?: ICorreccion[],
        public notificacionInfos?: INotificacionInfo[],
        public biblioteca?: IBiblioteca
    ) {}
}
