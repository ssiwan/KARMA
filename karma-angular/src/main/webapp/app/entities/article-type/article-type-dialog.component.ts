import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ArticleType } from './article-type.model';
import { ArticleTypePopupService } from './article-type-popup.service';
import { ArticleTypeService } from './article-type.service';
import { Article, ArticleService } from '../article';

@Component({
    selector: 'jhi-article-type-dialog',
    templateUrl: './article-type-dialog.component.html'
})
export class ArticleTypeDialogComponent implements OnInit {

    articleType: ArticleType;
    isSaving: boolean;

    articles: Article[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private articleTypeService: ArticleTypeService,
        private articleService: ArticleService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.articleService.query()
            .subscribe((res: HttpResponse<Article[]>) => { this.articles = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.articleType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.articleTypeService.update(this.articleType));
        } else {
            this.subscribeToSaveResponse(
                this.articleTypeService.create(this.articleType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ArticleType>>) {
        result.subscribe((res: HttpResponse<ArticleType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ArticleType) {
        this.eventManager.broadcast({ name: 'articleTypeListModification', content: 'OK'});
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

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-article-type-popup',
    template: ''
})
export class ArticleTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleTypePopupService: ArticleTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.articleTypePopupService
                    .open(ArticleTypeDialogComponent as Component, params['id']);
            } else {
                this.articleTypePopupService
                    .open(ArticleTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
