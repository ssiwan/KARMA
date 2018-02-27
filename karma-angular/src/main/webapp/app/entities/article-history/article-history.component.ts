import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticleHistory } from './article-history.model';
import { ArticleHistoryService } from './article-history.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-article-history',
    templateUrl: './article-history.component.html'
})
export class ArticleHistoryComponent implements OnInit, OnDestroy {
articleHistories: ArticleHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private articleHistoryService: ArticleHistoryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.articleHistoryService.query().subscribe(
            (res: HttpResponse<ArticleHistory[]>) => {
                this.articleHistories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArticleHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ArticleHistory) {
        return item.id;
    }
    registerChangeInArticleHistories() {
        this.eventSubscriber = this.eventManager.subscribe('articleHistoryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
