import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TagHistory } from './tag-history.model';
import { TagHistoryService } from './tag-history.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tag-history',
    templateUrl: './tag-history.component.html'
})
export class TagHistoryComponent implements OnInit, OnDestroy {
tagHistories: TagHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tagHistoryService: TagHistoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tagHistoryService.query().subscribe(
            (res: HttpResponse<TagHistory[]>) => {
                this.tagHistories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTagHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TagHistory) {
        return item.id;
    }
    registerChangeInTagHistories() {
        this.eventSubscriber = this.eventManager.subscribe('tagHistoryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
