import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    EstudianteComponent,
    EstudianteDetailComponent,
    EstudianteUpdateComponent,
    EstudianteDeletePopupComponent,
    EstudianteDeleteDialogComponent,
    estudianteRoute,
    estudiantePopupRoute
} from './';

const ENTITY_STATES = [...estudianteRoute, ...estudiantePopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EstudianteComponent,
        EstudianteDetailComponent,
        EstudianteUpdateComponent,
        EstudianteDeleteDialogComponent,
        EstudianteDeletePopupComponent
    ],
    entryComponents: [EstudianteComponent, EstudianteUpdateComponent, EstudianteDeleteDialogComponent, EstudianteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekEstudianteModule {}
