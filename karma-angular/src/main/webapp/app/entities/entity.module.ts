import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { KarmaAngularSpaceModule } from './space/space.module';
import { KarmaAngularArticleModule } from './article/article.module';
import { KarmaAngularTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        KarmaAngularSpaceModule,
        KarmaAngularArticleModule,
        KarmaAngularTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class KarmaAngularEntityModule {}
