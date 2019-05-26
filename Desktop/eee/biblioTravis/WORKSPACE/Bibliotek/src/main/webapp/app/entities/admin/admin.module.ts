import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    AdminComponent,
    AdminDetailComponent,
    AdminUpdateComponent,
    AdminDeletePopupComponent,
    AdminDeleteDialogComponent,
    adminRoute,
    adminPopupRoute
} from './';

const ENTITY_STATES = [...adminRoute, ...adminPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AdminComponent, AdminDetailComponent, AdminUpdateComponent, AdminDeleteDialogComponent, AdminDeletePopupComponent],
    entryComponents: [AdminComponent, AdminUpdateComponent, AdminDeleteDialogComponent, AdminDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekAdminModule {}
