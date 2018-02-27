import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ArticleType } from './article-type.model';
import { ArticleTypePopupService } from './article-type-popup.service';
import { ArticleTypeService } from './article-type.service';

@Component({
    selector: 'jhi-article-type-delete-dialog',
    templateUrl: './article-type-delete-dialog.component.html'
})
export class ArticleTypeDeleteDialogComponent {

    articleType: ArticleType;

    constructor(
        private articleTypeService: ArticleTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.articleTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'articleTypeListModification',
                content: 'Deleted an articleType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-article-type-delete-popup',
    template: ''
})
export class ArticleTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private articleTypePopupService: ArticleTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.articleTypePopupService
                .open(ArticleTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
