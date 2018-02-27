import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ArticleHistoryComponent } from './article-history.component';
import { ArticleHistoryDetailComponent } from './article-history-detail.component';
import { ArticleHistoryPopupComponent } from './article-history-dialog.component';
import { ArticleHistoryDeletePopupComponent } from './article-history-delete-dialog.component';

export const articleHistoryRoute: Routes = [
    {
        path: 'article-history',
        component: ArticleHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'article-history/:id',
        component: ArticleHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const articleHistoryPopupRoute: Routes = [
    {
        path: 'article-history-new',
        component: ArticleHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-history/:id/edit',
        component: ArticleHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'article-history/:id/delete',
        component: ArticleHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ArticleHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
