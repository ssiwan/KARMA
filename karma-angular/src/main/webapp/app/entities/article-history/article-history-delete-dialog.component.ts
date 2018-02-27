import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleHistory } from './article-history.model';
import { ArticleHistoryPopupService } from './article-history-popup.service';
import { ArticleHistoryService } from './article-history.service';

@Component({
    selector: 'jhi-article-history-delete-dialog',
    templateUrl: './article-history-delete-dialog.component.html'
})
export class ArticleHistoryDeleteDialogComponent {

    articleHistory: ArticleHistory;

    constructor(
        private articleHistoryService: ArticleHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.articleHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'articleHistoryListModification',
                content: 'Deleted an articleHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-article-history-delete-popup',
    template: ''
})
export class ArticleHistoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleHistoryPopupService: ArticleHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.articleHistoryPopupService
                .open(ArticleHistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
