import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TagHistory } from './tag-history.model';
import { TagHistoryPopupService } from './tag-history-popup.service';
import { TagHistoryService } from './tag-history.service';

@Component({
    selector: 'jhi-tag-history-delete-dialog',
    templateUrl: './tag-history-delete-dialog.component.html'
})
export class TagHistoryDeleteDialogComponent {

    tagHistory: TagHistory;

    constructor(
        private tagHistoryService: TagHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tagHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tagHistoryListModification',
                content: 'Deleted an tagHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tag-history-delete-popup',
    template: ''
})
export class TagHistoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tagHistoryPopupService: TagHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tagHistoryPopupService
                .open(TagHistoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
