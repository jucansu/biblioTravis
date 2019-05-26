import { IReporte } from 'app/shared/model/reporte.model';
import { IBiblioteca } from 'app/shared/model/biblioteca.model';
import { IAviso } from 'app/shared/model/aviso.model';

export interface IEstudiante {
    id?: number;
    reportes?: IReporte[];
    bibliotecas?: IBiblioteca[];
    avisos?: IAviso[];
    idBibliotecaEsta?: number;
    codigoQR?: String;
}

export class Estudiante implements IEstudiante {
    constructor(
        public id?: number,
        public reportes?: IReporte[],
        public bibliotecas?: IBiblioteca[],
        public avisos?: IAviso[],
        public idBibliotecaEsta?: number,
        public codigoQR?: String
    ) {}
}
