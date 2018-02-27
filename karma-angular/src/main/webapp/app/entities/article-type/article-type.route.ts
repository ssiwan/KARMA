import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ArticleTypeComponent } from './article-type.component';
import { ArticleTypeDetailComponent } from './article-type-detail.component';
import { ArticleTypePopupComponent } from './article-type-dialog.component';
import { ArticleTypeDeletePopupComponent } from './article-type-delete-dialog.component';

export const articleTypeRoute: Routes = [
    {
        path: 'article-type',
        component: ArticleTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'article-type/:id',
        component: ArticleTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const articleTypePopupRoute: Routes = [
    {
        path: 'article-type-new',
        component: ArticleTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-type/:id/edit',
        component: ArticleTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-type/:id/delete',
        component: ArticleTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
