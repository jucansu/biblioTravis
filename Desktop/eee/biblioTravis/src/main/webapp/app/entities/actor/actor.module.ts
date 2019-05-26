import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    ActorComponent,
    ActorDetailComponent,
    ActorUpdateComponent,
    ActorDeletePopupComponent,
    ActorDeleteDialogComponent,
    actorRoute,
    actorPopupRoute
} from './';

const ENTITY_STATES = [...actorRoute, ...actorPopupRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ActorComponent, ActorDetailComponent, ActorUpdateComponent, ActorDeleteDialogComponent, ActorDeletePopupComponent],
    entryComponents: [ActorComponent, ActorUpdateComponent, ActorDeleteDialogComponent, ActorDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekActorModule {}
