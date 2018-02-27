import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleHistory } from './article-history.model';
import { ArticleHistoryService } from './article-history.service';

@Component({
    selector: 'jhi-article-history-detail',
    templateUrl: './article-history-detail.component.html'
})
export class ArticleHistoryDetailComponent implements OnInit, OnDestroy {

    articleHistory: ArticleHistory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private articleHistoryService: ArticleHistoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticleHistories();
    }

    load(id) {
        this.articleHistoryService.find(id)
            .subscribe((articleHistoryResponse: HttpResponse<ArticleHistory>) => {
                this.articleHistory = articleHistoryResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticleHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articleHistoryListModification',
            (response) => this.load(this.articleHistory.id)
        );
    }
}
