import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ArticleHistory } from './article-history.model';
import { ArticleHistoryService } from './article-history.service';

@Injectable()
export class ArticleHistoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private articleHistoryService: ArticleHistoryService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.articleHistoryService.find(id)
                    .subscribe((articleHistoryResponse: HttpResponse<ArticleHistory>) => {
                        const articleHistory: ArticleHistory = articleHistoryResponse.body;
                        articleHistory.dateAccessed = this.datePipe
                            .transform(articleHistory.dateAccessed, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.articleHistoryModalRef(component, articleHistory);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.articleHistoryModalRef(component, new ArticleHistory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    articleHistoryModalRef(component: Component, articleHistory: ArticleHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.articleHistory = articleHistory;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
