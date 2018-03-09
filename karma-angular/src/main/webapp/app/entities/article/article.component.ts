import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiParseLinks, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Article } from './article.model';
import { ArticleService } from './article.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { Data } from '../../data';

@Component({
    selector: 'jhi-article',
    templateUrl: './article.component.html'
})
export class ArticleComponent implements OnInit, OnDestroy {

    articles: Article[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    public filterHeading: string;
    public showAll: boolean;

    constructor(
        private articleService: ArticleService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private data: Data,
        private principal: Principal
    ) {
        this.articles = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.articleService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<Article[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    reset() {
        this.page = 0;
        this.articles = [];
        this.loadData();
    }

    loadPage( page ) {
        this.page = page;
        this.loadData();
    }

    loadData() {
      if (this.data.all === false) {
        this.filterHeading = this.data.heading;
        this.articleService.filterArticles(this.data.routingPath, this.data.param).subscribe(
          (res: HttpResponse<Article[]>) => this.filterOnSuccess(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      } else {
        this.showAll = true;
        this.loadAll();
      }
    }

    ngOnInit() {
        this.loadData();

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInArticles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Article) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInArticles() {
        this.eventSubscriber = this.eventManager.subscribe('articleListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.articles.push(data[i]);
        }
    }

    private filterOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.articles.push(data[i]);
    }
  }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
