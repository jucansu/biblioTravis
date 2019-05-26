import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    ReporteComponent,
    ReporteDetailComponent,
    ReporteUpdateComponent,
    ReporteDeletePopupComponent,
    ReporteDeleteDialogComponent,
    reporteRoute,
    reportePopupRoute
} from './';

const ENTITY_STATES = [...reporteRoute, ...reportePopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReporteComponent,
        ReporteDetailComponent,
        ReporteUpdateComponent,
        ReporteDeleteDialogComponent,
        ReporteDeletePopupComponent
    ],
    entryComponents: [ReporteComponent, ReporteUpdateComponent, ReporteDeleteDialogComponent, ReporteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekReporteModule {}
