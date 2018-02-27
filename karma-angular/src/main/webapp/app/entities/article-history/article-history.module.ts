import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarmaAngularSharedModule } from '../../shared';
import { KarmaAngularAdminModule } from '../../admin/admin.module';
import {
    ArticleHistoryService,
    ArticleHistoryPopupService,
    ArticleHistoryComponent,
    ArticleHistoryDetailComponent,
    ArticleHistoryDialogComponent,
    ArticleHistoryPopupComponent,
    ArticleHistoryDeletePopupComponent,
    ArticleHistoryDeleteDialogComponent,
    articleHistoryRoute,
    articleHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...articleHistoryRoute,
    ...articleHistoryPopupRoute,
];

@NgModule({
    imports: [
        KarmaAngularSharedModule,
        KarmaAngularAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticleHistoryComponent,
        ArticleHistoryDetailComponent,
        ArticleHistoryDialogComponent,
        ArticleHistoryDeleteDialogComponent,
        ArticleHistoryPopupComponent,
        ArticleHistoryDeletePopupComponent,
    ],
    entryComponents: [
        ArticleHistoryComponent,
        ArticleHistoryDialogComponent,
        ArticleHistoryPopupComponent,
        ArticleHistoryDeleteDialogComponent,
        ArticleHistoryDeletePopupComponent,
    ],
    providers: [
        ArticleHistoryService,
        ArticleHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularArticleHistoryModule {}
