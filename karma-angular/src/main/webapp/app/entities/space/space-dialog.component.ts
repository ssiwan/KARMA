import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Space } from './space.model';
import { SpacePopupService } from './space-popup.service';
import { SpaceService } from './space.service';
import { User, UserService, Principal } from '../../shared';

@Component({
    selector: 'jhi-space-dialog',
    templateUrl: './space-dialog.component.html'
})
export class SpaceDialogComponent implements OnInit {

    space: Space;
    isSaving: boolean;
    account: any;

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private spaceService: SpaceService,
        private userService: UserService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    ngOnInit() {
      this.principal.identity().then((account) => {
        this.account = account;

      });

        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.space.id !== undefined) {
            this.subscribeToSaveResponse(
                this.spaceService.update(this.space));
        } else {
            this.space.user = this.account;
            this.space.handle = this.space.name;
            this.subscribeToSaveResponse(
                this.spaceService.create(this.space));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Space>>) {
        result.subscribe((res: HttpResponse<Space>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Space) {
        this.eventManager.broadcast({ name: 'spaceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-space-popup',
    template: ''
})
export class SpacePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spacePopupService: SpacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.spacePopupService
                    .open(SpaceDialogComponent as Component, params['id']);
            } else {
                this.spacePopupService
                    .open(SpaceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
