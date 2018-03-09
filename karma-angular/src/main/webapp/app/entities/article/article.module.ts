import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarmaAngularSharedModule } from '../../shared';
import { KarmaAngularAdminModule } from '../../admin/admin.module';
import {
    ArticleService,
    ArticlePopupService,
    ArticleComponent,
    ArticleDetailComponent,
    ArticleDialogComponent,
    ArticlePopupComponent,
    ArticleDeletePopupComponent,
    ArticleDeleteDialogComponent,
    articleRoute,
    articlePopupRoute,
} from './';

const ENTITY_STATES = [
    ...articleRoute,
    ...articlePopupRoute,
];

@NgModule({
    imports: [
        KarmaAngularSharedModule,
        KarmaAngularAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticleComponent,
        ArticleDetailComponent,
        ArticleDialogComponent,
        ArticleDeleteDialogComponent,
        ArticlePopupComponent,
        ArticleDeletePopupComponent,
    ],
    entryComponents: [
        ArticleComponent,
        ArticleDialogComponent,
        ArticlePopupComponent,
        ArticleDeleteDialogComponent,
        ArticleDeletePopupComponent,
    ],
    providers: [
        ArticleService,
        ArticlePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularArticleModule {}
