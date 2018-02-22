import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Space } from './space.model';
import { SpacePopupService } from './space-popup.service';
import { SpaceService } from './space.service';

@Component({
    selector: 'jhi-space-delete-dialog',
    templateUrl: './space-delete-dialog.component.html'
})
export class SpaceDeleteDialogComponent {

    space: Space;

    constructor(
        private spaceService: SpaceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.spaceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'spaceListModification',
                content: 'Deleted an space'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-space-delete-popup',
    template: ''
})
export class SpaceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private spacePopupService: SpacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.spacePopupService
                .open(SpaceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
