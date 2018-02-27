import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SpaceHistory } from './space-history.model';
import { SpaceHistoryPopupService } from './space-history-popup.service';
import { SpaceHistoryService } from './space-history.service';

@Component({
    selector: 'jhi-space-history-delete-dialog',
    templateUrl: './space-history-delete-dialog.component.html'
})
export class SpaceHistoryDeleteDialogComponent {

    spaceHistory: SpaceHistory;

    constructor(
        private spaceHistoryService: SpaceHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.spaceHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'spaceHistoryListModification',
                content: 'Deleted an spaceHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-space-history-delete-popup',
    template: ''
})
export class SpaceHistoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spaceHistoryPopupService: SpaceHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.spaceHistoryPopupService
                .open(SpaceHistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
