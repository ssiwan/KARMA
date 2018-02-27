import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleType } from './article-type.model';
import { ArticleTypeService } from './article-type.service';

@Component({
    selector: 'jhi-article-type-detail',
    templateUrl: './article-type-detail.component.html'
})
export class ArticleTypeDetailComponent implements OnInit, OnDestroy {

    articleType: ArticleType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private articleTypeService: ArticleTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInArticleTypes();
    }

    load(id) {
        this.articleTypeService.find(id)
            .subscribe((articleTypeResponse: HttpResponse<ArticleType>) => {
                this.articleType = articleTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInArticleTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'articleTypeListModification',
            (response) => this.load(this.articleType.id)
        );
    }
}
