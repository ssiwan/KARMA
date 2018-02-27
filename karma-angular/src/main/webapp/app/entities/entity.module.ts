import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KarmaAngularSpaceModule } from './space/space.module';
import { KarmaAngularArticleModule } from './article/article.module';
import { KarmaAngularTagModule } from './tag/tag.module';
import { KarmaAngularArticleHistoryModule } from './article-history/article-history.module';
import { KarmaAngularSpaceHistoryModule } from './space-history/space-history.module';
import { KarmaAngularTagHistoryModule } from './tag-history/tag-history.module';
import { KarmaAngularArticleTypeModule } from './article-type/article-type.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        KarmaAngularSpaceModule,
        KarmaAngularArticleModule,
        KarmaAngularTagModule,
        KarmaAngularArticleHistoryModule,
        KarmaAngularSpaceHistoryModule,
        KarmaAngularTagHistoryModule,
        KarmaAngularArticleTypeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularEntityModule {}
