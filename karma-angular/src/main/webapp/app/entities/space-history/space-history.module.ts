import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarmaAngularSharedModule } from '../../shared';
import { KarmaAngularAdminModule } from '../../admin/admin.module';
import {
    SpaceHistoryService,
    SpaceHistoryPopupService,
    SpaceHistoryComponent,
    SpaceHistoryDetailComponent,
    SpaceHistoryDialogComponent,
    SpaceHistoryPopupComponent,
    SpaceHistoryDeletePopupComponent,
    SpaceHistoryDeleteDialogComponent,
    spaceHistoryRoute,
    spaceHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...spaceHistoryRoute,
    ...spaceHistoryPopupRoute,
];

@NgModule({
    imports: [
        KarmaAngularSharedModule,
        KarmaAngularAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SpaceHistoryComponent,
        SpaceHistoryDetailComponent,
        SpaceHistoryDialogComponent,
        SpaceHistoryDeleteDialogComponent,
        SpaceHistoryPopupComponent,
        SpaceHistoryDeletePopupComponent,
    ],
    entryComponents: [
        SpaceHistoryComponent,
        SpaceHistoryDialogComponent,
        SpaceHistoryPopupComponent,
        SpaceHistoryDeleteDialogComponent,
        SpaceHistoryDeletePopupComponent,
    ],
    providers: [
        SpaceHistoryService,
        SpaceHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularSpaceHistoryModule {}
