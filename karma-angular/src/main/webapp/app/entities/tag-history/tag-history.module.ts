import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarmaAngularSharedModule } from '../../shared';
import { KarmaAngularAdminModule } from '../../admin/admin.module';
import {
    TagHistoryService,
    TagHistoryPopupService,
    TagHistoryComponent,
    TagHistoryDetailComponent,
    TagHistoryDialogComponent,
    TagHistoryPopupComponent,
    TagHistoryDeletePopupComponent,
    TagHistoryDeleteDialogComponent,
    tagHistoryRoute,
    tagHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tagHistoryRoute,
    ...tagHistoryPopupRoute,
];

@NgModule({
    imports: [
        KarmaAngularSharedModule,
        KarmaAngularAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TagHistoryComponent,
        TagHistoryDetailComponent,
        TagHistoryDialogComponent,
        TagHistoryDeleteDialogComponent,
        TagHistoryPopupComponent,
        TagHistoryDeletePopupComponent,
    ],
    entryComponents: [
        TagHistoryComponent,
        TagHistoryDialogComponent,
        TagHistoryPopupComponent,
        TagHistoryDeleteDialogComponent,
        TagHistoryDeletePopupComponent,
    ],
    providers: [
        TagHistoryService,
        TagHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularTagHistoryModule {}
