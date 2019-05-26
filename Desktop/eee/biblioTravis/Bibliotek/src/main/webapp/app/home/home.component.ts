import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { BibliotecaService } from '../entities/biblioteca/biblioteca.service';
import { EstudianteService } from '../entities/estudiante/estudiante.service';
import { IBiblioteca } from '../shared/model/biblioteca.model';
import { IEstudiante } from '../shared/model/estudiante.model';
import { IUser } from '../core/user/user.model';

import { LoginModalService, AccountService, Account } from 'app/core';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    bibliotecas: IBiblioteca[];
    bibliotecasMias: IBiblioteca[];
    estudiante: IEstudiante;
    qr: String;
    bibliotecaDetalle: IBiblioteca;
    display = 'none';
    display2 = 'none';
    display3 = 'none';
    estudianteEsta: number;
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
    detalleDireccion?: String;
    detalleFotoDetalle?: String;
    detalleUrlDireccion?: string;
    detalleNumVotos?: number;
    detalleId?: number;

    mia: boolean;
    noMostrarAdmin: boolean;

    openModalDialog() {
        this.display = 'block'; // Set block css
        this.traerEstudiante();
    }

    openModalDialogDetalle(biblioteca: IBiblioteca) {
        this.display2 = 'block'; // Set block css
        this.detalleNombre = biblioteca.nombre;
        this.detalleDescripcion = biblioteca.descripcion;
        this.detalleEnchufes = biblioteca.numEnchufes;
        this.detalleFoto = biblioteca.foto;
        this.detallePlazasOcupadas = biblioteca.plazasOcupadas;
        this.detallePlazasTotales = biblioteca.plazasTotales;
        this.detalleValoracion = Math.round(biblioteca.valoracion);
        this.detalleZona = biblioteca.zona;
        this.detallePlazasLibres = this.detallePlazasTotales - this.detallePlazasOcupadas;
        this.detalleHora = biblioteca.horario;
        this.detalleDireccion = biblioteca.direccion;
        this.detalleFotoDetalle = biblioteca.fotoDetalle;
        this.detalleUrlDireccion = biblioteca.urlDireccion;
        this.detalleNumVotos = biblioteca.numVotos;
        this.detalleId = biblioteca.id;
    }

    closeModalDialog() {
        this.display = 'none'; // set none css after close dialog
    }

    closeModalDialogDetalle() {
        this.display2 = 'none'; // set none css after close dialog
    }

    constructor(
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private bibliotecaService: BibliotecaService,
        private estudianteService: EstudianteService
    ) { }

    ngOnInit() {
        this.bibliotecas = [];
        this.accountService.identity().then((account: Account) => {
            this.account = account;
            if(account==undefined){
                this.noMostrarAdmin=true;
            }else{
            if(account.firstName=='Administrator'){
                this.noMostrarAdmin=false;
             }else {
                this.noMostrarAdmin=true;
                this.traerEstudiantesEsta();
                this.traerMisBibliotecas();
            }
        }

           
           
        });
        this.registerAuthenticationSuccess();
        console.log('------------------------------------ENTRAR');
        this.traerBibliotecasOrden();
        console.log('------------------------------------SALIR');
        this.mia = false;
    }

    anadirMia() {
        this.mia = true;
        this.display3 = 'block';
    }
    quitarMia() {
        this.mia = false;
    }

    closeModalDialoganadir() {
        this.display3 = 'none'; // set none css after close dialog
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    traerBibliotecas() {
        this._traerBibliotecas().then((res: IBiblioteca[]) => {
            console.log(res);
            this.bibliotecas = res;

            /* this._loadingService.resolve(); */
        });
    }

    pausa(id: number) {
        this._pausar(50).then((res: IEstudiante) => {
            console.log(res, 'pausado!!!!');
        });
    }
    traerBibliotecasOrden() {
        this._traerBibliotecasOrden().then((res: IBiblioteca[]) => {
            console.log(res);
            this.bibliotecas = res;
            this.bibliotecas.forEach(a => {
                let plazasDisponibles: number;

                plazasDisponibles = a.plazasTotales - a.plazasOcupadas;
                a.plazasDisp = plazasDisponibles;
            });

            /* this._loadingService.resolve(); */
        });
    }
    traerMisBibliotecas() {
        this._traerMisBibliotecas(this.account.id).then((res: IBiblioteca[]) => {
            console.log(res);
            this.bibliotecasMias = res;
        });
    }

    traerEstudiantesEsta() {
        this._traerEstudiante(this.account.id).then((res: IEstudiante) => {
            console.log(res);
            this.estudiante = res;
            this.estudianteEsta = this.estudiante.idBibliotecaEsta;
        });
    }
    llevarAMapa() {
        window.open(this.detalleUrlDireccion, '_blank');
    }

    traerEstudiante() {
        const idEstudiante: number = this.account.id;
        const nombre: String = this.account.login;

        this.qr = '../content/tmp/' + nombre + '.png';
    }
    votoPositivo(id: number) {
        this._puntuaPositivo(id).then((res: IBiblioteca[]) => {
            console.log(res, 'puntuado positivo!!!!');
        });
    }
    votoNegativo(id: number) {
        this._puntuaNegativo(id).then((res: IBiblioteca[]) => {
            console.log(res, 'puntuado negativo!!!!');
        });
    }

    async _puntuaPositivo(id: number): Promise<IBiblioteca[]> {
        try {
            return await this.bibliotecaService.puntuaPositivo(id).toPromise();
            console.log('Puntua positivo al ' + id);
        } catch (error) {
            window.alert('error en consulta de bibliotecas ' + error);

            throw error;
        }
    }
    async _pausar(id: number): Promise<IEstudiante> {
        try {
            return await this.estudianteService.pausa(id).toPromise();
            console.log('Puntua positivo al ' + id);
        } catch (error) {
            window.alert('error en consulta de bibliotecas ' + error);

            throw error;
        }
    }
    async _puntuaNegativo(id: number): Promise<IBiblioteca[]> {
        try {
            return await this.bibliotecaService.puntuaNegativo(id).toPromise();
            console.log('Puntua negativo al ' + id);
        } catch (error) {
            window.alert(' error en consulta de bibliotecas ' + error);

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

    async _traerBibliotecasOrden(): Promise<IBiblioteca[]> {
        try {
            return await this.bibliotecaService.traeBibliotecaOrden().toPromise();
        } catch (error) {
            window.alert('error en consulta de bibliotecas ' + error);

            throw error;
        }
    }

    async _traerMisBibliotecas(id: number): Promise<IBiblioteca[]> {
        try {
            return await this.bibliotecaService.traeMisBibliotecas(id).toPromise();
        } catch (error) {
            window.alert('error en consulta de sus bibliotecas ' + error);

            throw error;
        }
    }

    async _traerEstudiante(idEstudiante: number): Promise<IEstudiante> {
        try {
            return await this.estudianteService.traeEstudiante(idEstudiante).toPromise();
        } catch (error) {
            window.alert('error en consulta de usuario para QR ' + error);

            throw error;
        }
    }
}
