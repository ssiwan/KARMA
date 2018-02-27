import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { KarmaAngularSharedModule } from '../../shared';
import {
    ArticleTypeService,
    ArticleTypePopupService,
    ArticleTypeComponent,
    ArticleTypeDetailComponent,
    ArticleTypeDialogComponent,
    ArticleTypePopupComponent,
    ArticleTypeDeletePopupComponent,
    ArticleTypeDeleteDialogComponent,
    articleTypeRoute,
    articleTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...articleTypeRoute,
    ...articleTypePopupRoute,
];

@NgModule({
    imports: [
        KarmaAngularSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ArticleTypeComponent,
        ArticleTypeDetailComponent,
        ArticleTypeDialogComponent,
        ArticleTypeDeleteDialogComponent,
        ArticleTypePopupComponent,
        ArticleTypeDeletePopupComponent,
    ],
    entryComponents: [
        ArticleTypeComponent,
        ArticleTypeDialogComponent,
        ArticleTypePopupComponent,
        ArticleTypeDeleteDialogComponent,
        ArticleTypeDeletePopupComponent,
    ],
    providers: [
        ArticleTypeService,
        ArticleTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularArticleTypeModule {}
