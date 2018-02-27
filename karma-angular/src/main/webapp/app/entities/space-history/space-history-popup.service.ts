import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { SpaceHistory } from './space-history.model';
import { SpaceHistoryService } from './space-history.service';

@Injectable()
export class SpaceHistoryPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private spaceHistoryService: SpaceHistoryService

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
                this.spaceHistoryService.find(id)
                    .subscribe((spaceHistoryResponse: HttpResponse<SpaceHistory>) => {
                        const spaceHistory: SpaceHistory = spaceHistoryResponse.body;
                        spaceHistory.dateAccessed = this.datePipe
                            .transform(spaceHistory.dateAccessed, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.spaceHistoryModalRef(component, spaceHistory);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.spaceHistoryModalRef(component, new SpaceHistory());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    spaceHistoryModalRef(component: Component, spaceHistory: SpaceHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.spaceHistory = spaceHistory;
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
