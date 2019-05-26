import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    AvisoComponent,
    AvisoDetailComponent,
    AvisoUpdateComponent,
    AvisoDeletePopupComponent,
    AvisoDeleteDialogComponent,
    avisoRoute,
    avisoPopupRoute
} from './';

const ENTITY_STATES = [...avisoRoute, ...avisoPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AvisoComponent, AvisoDetailComponent, AvisoUpdateComponent, AvisoDeleteDialogComponent, AvisoDeletePopupComponent],
    entryComponents: [AvisoComponent, AvisoUpdateComponent, AvisoDeleteDialogComponent, AvisoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekAvisoModule {}
