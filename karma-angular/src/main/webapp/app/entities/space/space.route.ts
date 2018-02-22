import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SpaceComponent } from './space.component';
import { SpaceDetailComponent } from './space-detail.component';
import { SpacePopupComponent } from './space-dialog.component';
import { SpaceDeletePopupComponent } from './space-delete-dialog.component';

export const spaceRoute: Routes = [
    {
        path: 'space',
        component: SpaceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spaces'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'space/:id',
        component: SpaceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spaces'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const spacePopupRoute: Routes = [
    {
        path: 'space-new',
        component: SpacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spaces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'space/:id/edit',
        component: SpacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spaces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'space/:id/delete',
        component: SpaceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Spaces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
