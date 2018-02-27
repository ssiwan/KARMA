import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SpaceHistoryComponent } from './space-history.component';
import { SpaceHistoryDetailComponent } from './space-history-detail.component';
import { SpaceHistoryPopupComponent } from './space-history-dialog.component';
import { SpaceHistoryDeletePopupComponent } from './space-history-delete-dialog.component';

export const spaceHistoryRoute: Routes = [
    {
        path: 'space-history',
        component: SpaceHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpaceHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'space-history/:id',
        component: SpaceHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpaceHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const spaceHistoryPopupRoute: Routes = [
    {
        path: 'space-history-new',
        component: SpaceHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpaceHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'space-history/:id/edit',
        component: SpaceHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpaceHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'space-history/:id/delete',
        component: SpaceHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SpaceHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
