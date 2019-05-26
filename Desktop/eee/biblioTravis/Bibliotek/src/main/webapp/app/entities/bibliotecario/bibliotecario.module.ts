import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    BibliotecarioComponent,
    BibliotecarioDetailComponent,
    BibliotecarioUpdateComponent,
    BibliotecarioDeletePopupComponent,
    BibliotecarioDeleteDialogComponent,
    bibliotecarioRoute,
    bibliotecarioPopupRoute
} from './';

const ENTITY_STATES = [...bibliotecarioRoute, ...bibliotecarioPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BibliotecarioComponent,
        BibliotecarioDetailComponent,
        BibliotecarioUpdateComponent,
        BibliotecarioDeleteDialogComponent,
        BibliotecarioDeletePopupComponent
    ],
    entryComponents: [
        BibliotecarioComponent,
        BibliotecarioUpdateComponent,
        BibliotecarioDeleteDialogComponent,
        BibliotecarioDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekBibliotecarioModule {}
