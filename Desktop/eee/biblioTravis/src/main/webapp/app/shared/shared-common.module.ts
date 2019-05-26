import { NgModule } from '@angular/core';

import { bibliotekSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [bibliotekSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [bibliotekSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class bibliotekSharedCommonModule {}
