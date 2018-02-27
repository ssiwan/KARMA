import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticleType } from './article-type.model';
import { ArticleTypeService } from './article-type.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-article-type',
    templateUrl: './article-type.component.html'
})
export class ArticleTypeComponent implements OnInit, OnDestroy {
articleTypes: ArticleType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private articleTypeService: ArticleTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.articleTypeService.query().subscribe(
            (res: HttpResponse<ArticleType[]>) => {
                this.articleTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArticleTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ArticleType) {
        return item.id;
    }
    registerChangeInArticleTypes() {
        this.eventSubscriber = this.eventManager.subscribe('articleTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
