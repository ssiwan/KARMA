import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarmaAngularSharedModule } from '../../shared';
import { KarmaAngularAdminModule } from '../../admin/admin.module';
import {
    SpaceService,
    SpacePopupService,
    SpaceComponent,
    SpaceDetailComponent,
    SpaceDialogComponent,
    SpacePopupComponent,
    SpaceDeletePopupComponent,
    SpaceDeleteDialogComponent,
    spaceRoute,
    spacePopupRoute,
} from './';

const ENTITY_STATES = [
    ...spaceRoute,
    ...spacePopupRoute,
];

@NgModule({
    imports: [
        KarmaAngularSharedModule,
        KarmaAngularAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SpaceComponent,
        SpaceDetailComponent,
        SpaceDialogComponent,
        SpaceDeleteDialogComponent,
        SpacePopupComponent,
        SpaceDeletePopupComponent,
    ],
    entryComponents: [
        SpaceComponent,
        SpaceDialogComponent,
        SpacePopupComponent,
        SpaceDeleteDialogComponent,
        SpaceDeletePopupComponent,
    ],
    providers: [
        SpaceService,
        SpacePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularSpaceModule {}
