import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SpaceHistory } from './space-history.model';
import { SpaceHistoryService } from './space-history.service';

@Component({
    selector: 'jhi-space-history-detail',
    templateUrl: './space-history-detail.component.html'
})
export class SpaceHistoryDetailComponent implements OnInit, OnDestroy {

    spaceHistory: SpaceHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private spaceHistoryService: SpaceHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpaceHistories();
    }

    load(id) {
        this.spaceHistoryService.find(id)
            .subscribe((spaceHistoryResponse: HttpResponse<SpaceHistory>) => {
                this.spaceHistory = spaceHistoryResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpaceHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'spaceHistoryListModification',
            (response) => this.load(this.spaceHistory.id)
        );
    }
}
