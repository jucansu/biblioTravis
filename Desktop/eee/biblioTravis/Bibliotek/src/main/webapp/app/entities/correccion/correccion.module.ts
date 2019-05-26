import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    CorreccionComponent,
    CorreccionDetailComponent,
    CorreccionUpdateComponent,
    CorreccionDeletePopupComponent,
    CorreccionDeleteDialogComponent,
    correccionRoute,
    correccionPopupRoute
} from './';

const ENTITY_STATES = [...correccionRoute, ...correccionPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CorreccionComponent,
        CorreccionDetailComponent,
        CorreccionUpdateComponent,
        CorreccionDeleteDialogComponent,
        CorreccionDeletePopupComponent
    ],
    entryComponents: [CorreccionComponent, CorreccionUpdateComponent, CorreccionDeleteDialogComponent, CorreccionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekCorreccionModule {}
