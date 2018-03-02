import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ArticleComponent } from './article.component';
import { ArticleDetailComponent } from './article-detail.component';
import { ArticlePopupComponent } from './article-dialog.component';
import { ArticleDeletePopupComponent } from './article-delete-dialog.component';
import { ArticleListComponent} from './article-list.component';

export const articleRoute: Routes = [
    {
        path: 'article',
        component: ArticleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Articles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'article/:id',
        component: ArticleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Articles'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'article-list',
        component: ArticleListComponent,
        data: {
          authories: ['ROLE_USER'],
          pageTitle: 'Article List'
        }
    }
];

export const articlePopupRoute: Routes = [
    {
        path: 'article-new',
        component: ArticlePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Articles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article/:id/edit',
        component: ArticlePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Articles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article/:id/delete',
        component: ArticleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Articles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
