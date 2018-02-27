import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SpaceHistory } from './space-history.model';
import { SpaceHistoryPopupService } from './space-history-popup.service';
import { SpaceHistoryService } from './space-history.service';
import { Space, SpaceService } from '../space';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-space-history-dialog',
    templateUrl: './space-history-dialog.component.html'
})
export class SpaceHistoryDialogComponent implements OnInit {

    spaceHistory: SpaceHistory;
    isSaving: boolean;

    spaces: Space[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private spaceHistoryService: SpaceHistoryService,
        private spaceService: SpaceService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.spaceService
            .query({filter: 'spacehistory-is-null'})
            .subscribe((res: HttpResponse<Space[]>) => {
                if (!this.spaceHistory.space || !this.spaceHistory.space.id) {
                    this.spaces = res.body;
                } else {
                    this.spaceService
                        .find(this.spaceHistory.space.id)
                        .subscribe((subRes: HttpResponse<Space>) => {
                            this.spaces = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.spaceHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.spaceHistoryService.update(this.spaceHistory));
        } else {
            this.subscribeToSaveResponse(
                this.spaceHistoryService.create(this.spaceHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SpaceHistory>>) {
        result.subscribe((res: HttpResponse<SpaceHistory>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SpaceHistory) {
        this.eventManager.broadcast({ name: 'spaceHistoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSpaceById(index: number, item: Space) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-space-history-popup',
    template: ''
})
export class SpaceHistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spaceHistoryPopupService: SpaceHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.spaceHistoryPopupService
                    .open(SpaceHistoryDialogComponent as Component, params['id']);
            } else {
                this.spaceHistoryPopupService
                    .open(SpaceHistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
