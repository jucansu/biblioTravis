import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'actor',
                loadChildren: './actor/actor.module#bibliotekActorModule'
            },
            {
                path: 'estudiante',
                loadChildren: './estudiante/estudiante.module#bibliotekEstudianteModule'
            },
            {
                path: 'bibliotecario',
                loadChildren: './bibliotecario/bibliotecario.module#bibliotekBibliotecarioModule'
            },
            {
                path: 'admin',
                loadChildren: './admin/admin.module#bibliotekAdminModule'
            },
            {
                path: 'administrativo',
                loadChildren: './administrativo/administrativo.module#bibliotekAdministrativoModule'
            },
            {
                path: 'aviso',
                loadChildren: './aviso/aviso.module#bibliotekAvisoModule'
            },
            {
                path: 'reporte',
                loadChildren: './reporte/reporte.module#bibliotekReporteModule'
            },
            {
                path: 'correccion',
                loadChildren: './correccion/correccion.module#bibliotekCorreccionModule'
            },
            {
                path: 'biblioteca',
                loadChildren: './biblioteca/biblioteca.module#bibliotekBibliotecaModule'
            },
            {
                path: 'sala-estudio',
                loadChildren: './sala-estudio/sala-estudio.module#bibliotekSalaEstudioModule'
            },
            {
                path: 'notificacion-info',
                loadChildren: './notificacion-info/notificacion-info.module#bibliotekNotificacionInfoModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekEntityModule {}
