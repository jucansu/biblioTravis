import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    AdministrativoComponent,
    AdministrativoDetailComponent,
    AdministrativoUpdateComponent,
    AdministrativoDeletePopupComponent,
    AdministrativoDeleteDialogComponent,
    administrativoRoute,
    administrativoPopupRoute
} from './';

const ENTITY_STATES = [...administrativoRoute, ...administrativoPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdministrativoComponent,
        AdministrativoDetailComponent,
        AdministrativoUpdateComponent,
        AdministrativoDeleteDialogComponent,
        AdministrativoDeletePopupComponent
    ],
    entryComponents: [
        AdministrativoComponent,
        AdministrativoUpdateComponent,
        AdministrativoDeleteDialogComponent,
        AdministrativoDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekAdministrativoModule {}
