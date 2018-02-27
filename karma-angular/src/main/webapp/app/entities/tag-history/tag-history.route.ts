import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TagHistoryComponent } from './tag-history.component';
import { TagHistoryDetailComponent } from './tag-history-detail.component';
import { TagHistoryPopupComponent } from './tag-history-dialog.component';
import { TagHistoryDeletePopupComponent } from './tag-history-delete-dialog.component';

export const tagHistoryRoute: Routes = [
    {
        path: 'tag-history',
        component: TagHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TagHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tag-history/:id',
        component: TagHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TagHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tagHistoryPopupRoute: Routes = [
    {
        path: 'tag-history-new',
        component: TagHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TagHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tag-history/:id/edit',
        component: TagHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TagHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tag-history/:id/delete',
        component: TagHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TagHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
