import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticleHistory } from './article-history.model';
import { ArticleHistoryPopupService } from './article-history-popup.service';
import { ArticleHistoryService } from './article-history.service';
import { Article, ArticleService } from '../article';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-article-history-dialog',
    templateUrl: './article-history-dialog.component.html'
})
export class ArticleHistoryDialogComponent implements OnInit {

    articleHistory: ArticleHistory;
    isSaving: boolean;

    articles: Article[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articleHistoryService: ArticleHistoryService,
        private articleService: ArticleService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.articleService.query()
            .subscribe((res: HttpResponse<Article[]>) => { this.articles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.articleHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articleHistoryService.update(this.articleHistory));
        } else {
            this.subscribeToSaveResponse(
                this.articleHistoryService.create(this.articleHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ArticleHistory>>) {
        result.subscribe((res: HttpResponse<ArticleHistory>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ArticleHistory) {
        this.eventManager.broadcast({ name: 'articleHistoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackArticleById(index: number, item: Article) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-article-history-popup',
    template: ''
})
export class ArticleHistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleHistoryPopupService: ArticleHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articleHistoryPopupService
                    .open(ArticleHistoryDialogComponent as Component, params['id']);
            } else {
                this.articleHistoryPopupService
                    .open(ArticleHistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
