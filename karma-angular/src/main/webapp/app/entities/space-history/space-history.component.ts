import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SpaceHistory } from './space-history.model';
import { SpaceHistoryService } from './space-history.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-space-history',
    templateUrl: './space-history.component.html'
})
export class SpaceHistoryComponent implements OnInit, OnDestroy {
spaceHistories: SpaceHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private spaceHistoryService: SpaceHistoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.spaceHistoryService.query().subscribe(
            (res: HttpResponse<SpaceHistory[]>) => {
                this.spaceHistories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSpaceHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SpaceHistory) {
        return item.id;
    }
    registerChangeInSpaceHistories() {
        this.eventSubscriber = this.eventManager.subscribe('spaceHistoryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
