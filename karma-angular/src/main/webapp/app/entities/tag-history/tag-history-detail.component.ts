import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TagHistory } from './tag-history.model';
import { TagHistoryService } from './tag-history.service';

@Component({
    selector: 'jhi-tag-history-detail',
    templateUrl: './tag-history-detail.component.html'
})
export class TagHistoryDetailComponent implements OnInit, OnDestroy {

    tagHistory: TagHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tagHistoryService: TagHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTagHistories();
    }

    load(id) {
        this.tagHistoryService.find(id)
            .subscribe((tagHistoryResponse: HttpResponse<TagHistory>) => {
                this.tagHistory = tagHistoryResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTagHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tagHistoryListModification',
            (response) => this.load(this.tagHistory.id)
        );
    }
}
