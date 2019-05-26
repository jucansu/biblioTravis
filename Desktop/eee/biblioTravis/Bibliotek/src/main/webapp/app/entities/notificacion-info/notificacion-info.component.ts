import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { BibliotecaService } from '../biblioteca/biblioteca.service';
import { EstudianteService } from '../estudiante/estudiante.service';
import { IBiblioteca } from '../../shared/model/biblioteca.model';
import { IEstudiante } from '../../shared/model/estudiante.model';
import { IUser } from '../../core/user/user.model';

import { AccountService } from 'app/core';
import { NotificacionInfoService } from './notificacion-info.service';

@Component({
    selector: 'jhi-notificacion-info',
    templateUrl: './notificacion-info.component.html'
})
export class NotificacionInfoComponent implements OnInit {
    currentAccount: any;
    eventSubscriber: Subscription;
    bibliotecas: IBiblioteca[];
    bibliotecasMias: IBiblioteca[];
    //detalle biblio
    detalleNombre: String;
    detalleDescripcion: String;
    detalleFoto: String;
    detallePlazasOcupadas: number;
    detallePlazasTotales: number;
    detalleValoracion: number;
    detalleZona: String;
    detalleEnchufes: number;
    detallePlazasLibres: number;
    detalleHora: String;
    display3 = 'none';
    detalleDireccion?: String;
    detalleFotoDetalle?: String;
    detalleUrlDireccion?: string;
    detalleNumVotos?: number;

    constructor(
        protected notificacionInfoService: NotificacionInfoService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService,
        private bibliotecaService: BibliotecaService,
        private estudianteService: EstudianteService
    ) {}

    ngOnInit() {
        this.bibliotecas = [];
        this.accountService.identity().then(account => {
            this.currentAccount = account;
            this.traerMisBibliotecas();
        });

        this.traerBibliotecas();
    }
    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    openModalDialogDetalle(biblioteca: IBiblioteca) {
        this.display3 = 'block'; //Set block css
        this.detalleNombre = biblioteca.nombre;
        this.detalleDescripcion = biblioteca.descripcion;
        this.detalleEnchufes = biblioteca.numEnchufes;
        this.detalleFoto = biblioteca.foto;
        this.detallePlazasOcupadas = biblioteca.plazasOcupadas;
        this.detallePlazasTotales = biblioteca.plazasTotales;
        this.detalleValoracion = biblioteca.valoracion;
        this.detalleZona = biblioteca.zona;
        this.detallePlazasLibres = this.detallePlazasTotales - this.detallePlazasOcupadas;
        this.detalleHora = biblioteca.horario;
        this.detalleDireccion = biblioteca.direccion;
        this.detalleFotoDetalle = biblioteca.fotoDetalle;
        this.detalleUrlDireccion = biblioteca.urlDireccion;
        this.detalleNumVotos = biblioteca.numVotos;
    }

    llevarAMapa() {
        window.open(this.detalleUrlDireccion, '_blank');
    }
    closeModalDialogDetalle() {
        this.display3 = 'none'; //set none css after close dialog
    }
    traerBibliotecas() {
        this._traerBibliotecas().then((res: IBiblioteca[]) => {
            console.log(res);
            this.bibliotecas = res;

            /* this._loadingService.resolve(); */
        });
    }

    traerMisBibliotecas() {
        this._traerMisBibliotecas(this.currentAccount.id).then((res: IBiblioteca[]) => {
            console.log(res);
            this.bibliotecasMias = res;
        });
    }

    async _traerMisBibliotecas(id: number): Promise<IBiblioteca[]> {
        try {
            return await this.bibliotecaService.traeMisBibliotecas(id).toPromise();
        } catch (error) {
            window.alert('error en consulta de sus bibliotecas ' + error);

            throw error;
        }
    }

    async _traerBibliotecas(): Promise<IBiblioteca[]> {
        try {
            return await this.bibliotecaService.traeBiblioteca().toPromise();
        } catch (error) {
            window.alert('error en consulta de bibliotecas ' + error);

            throw error;
        }
    }
}
