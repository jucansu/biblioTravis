import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    BibliotecaComponent,
    BibliotecaDetailComponent,
    BibliotecaUpdateComponent,
    BibliotecaDeletePopupComponent,
    BibliotecaDeleteDialogComponent,
    bibliotecaRoute,
    bibliotecaPopupRoute
} from './';

const ENTITY_STATES = [...bibliotecaRoute, ...bibliotecaPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BibliotecaComponent,
        BibliotecaDetailComponent,
        BibliotecaUpdateComponent,
        BibliotecaDeleteDialogComponent,
        BibliotecaDeletePopupComponent
    ],
    entryComponents: [BibliotecaComponent, BibliotecaUpdateComponent, BibliotecaDeleteDialogComponent, BibliotecaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekBibliotecaModule {}
