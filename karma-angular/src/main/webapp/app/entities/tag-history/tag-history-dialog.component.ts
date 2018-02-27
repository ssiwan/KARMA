import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TagHistory } from './tag-history.model';
import { TagHistoryPopupService } from './tag-history-popup.service';
import { TagHistoryService } from './tag-history.service';
import { Tag, TagService } from '../tag';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-tag-history-dialog',
    templateUrl: './tag-history-dialog.component.html'
})
export class TagHistoryDialogComponent implements OnInit {

    tagHistory: TagHistory;
    isSaving: boolean;

    tags: Tag[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tagHistoryService: TagHistoryService,
        private tagService: TagService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tagService
            .query({filter: 'taghistory-is-null'})
            .subscribe((res: HttpResponse<Tag[]>) => {
                if (!this.tagHistory.tag || !this.tagHistory.tag.id) {
                    this.tags = res.body;
                } else {
                    this.tagService
                        .find(this.tagHistory.tag.id)
                        .subscribe((subRes: HttpResponse<Tag>) => {
                            this.tags = [subRes.body].concat(res.body);
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
        if (this.tagHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tagHistoryService.update(this.tagHistory));
        } else {
            this.subscribeToSaveResponse(
                this.tagHistoryService.create(this.tagHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TagHistory>>) {
        result.subscribe((res: HttpResponse<TagHistory>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TagHistory) {
        this.eventManager.broadcast({ name: 'tagHistoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tag-history-popup',
    template: ''
})
export class TagHistoryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tagHistoryPopupService: TagHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tagHistoryPopupService
                    .open(TagHistoryDialogComponent as Component, params['id']);
            } else {
                this.tagHistoryPopupService
                    .open(TagHistoryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
