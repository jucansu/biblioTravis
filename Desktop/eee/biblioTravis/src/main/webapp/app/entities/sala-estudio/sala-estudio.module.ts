import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    SalaEstudioComponent,
    SalaEstudioDetailComponent,
    SalaEstudioUpdateComponent,
    SalaEstudioDeletePopupComponent,
    SalaEstudioDeleteDialogComponent,
    salaEstudioRoute,
    salaEstudioPopupRoute
} from './';

const ENTITY_STATES = [...salaEstudioRoute, ...salaEstudioPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SalaEstudioComponent,
        SalaEstudioDetailComponent,
        SalaEstudioUpdateComponent,
        SalaEstudioDeleteDialogComponent,
        SalaEstudioDeletePopupComponent
    ],
    entryComponents: [SalaEstudioComponent, SalaEstudioUpdateComponent, SalaEstudioDeleteDialogComponent, SalaEstudioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekSalaEstudioModule {}
