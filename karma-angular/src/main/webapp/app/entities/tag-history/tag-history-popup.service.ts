import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { TagHistory } from './tag-history.model';
import { TagHistoryService } from './tag-history.service';

@Injectable()
export class TagHistoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private tagHistoryService: TagHistoryService

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
                this.tagHistoryService.find(id)
                    .subscribe((tagHistoryResponse: HttpResponse<TagHistory>) => {
                        const tagHistory: TagHistory = tagHistoryResponse.body;
                        tagHistory.dateAccessed = this.datePipe
                            .transform(tagHistory.dateAccessed, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.tagHistoryModalRef(component, tagHistory);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tagHistoryModalRef(component, new TagHistory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tagHistoryModalRef(component: Component, tagHistory: TagHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tagHistory = tagHistory;
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
