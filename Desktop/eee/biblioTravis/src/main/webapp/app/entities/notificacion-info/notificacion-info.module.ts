import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { bibliotekSharedModule } from 'app/shared';
import {
    NotificacionInfoComponent,
    notificacionInfoRoute,
   
} from './';

const ENTITY_STATES = [...notificacionInfoRoute];

@NgModule({
    imports: [bibliotekSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NotificacionInfoComponent,
       
    ],
    entryComponents: [
        NotificacionInfoComponent,
      
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class bibliotekNotificacionInfoModule {}
