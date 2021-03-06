import { ArticleService } from '../article';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Space } from './space.model';
import { SpacePopupService } from './space-popup.service';
import { SpaceService } from './space.service';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-space-delete-dialog',
    templateUrl: './space-delete-dialog.component.html'
})
export class SpaceDeleteDialogComponent {

    space: Space;
    articleCount: number;

    constructor(
        private articleService: ArticleService,
        private spaceService: SpaceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private jhiAlertService: JhiAlertService
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
      this.articleService.countBySpace(id).subscribe(
        (res: HttpResponse<number>) => {
          this.articleCount = res.body;

          if (this.articleCount === 0) {
            this.spaceService.delete(id).subscribe((response) => {
              this.eventManager.broadcast({
                name: 'spaceListModification',
                content: 'Deleted an space'
              });
              this.activeModal.dismiss(true);
            });
          } else {
            this.activeModal.dismiss(true);
            this.jhiAlertService.error('Must delete associated Articles first', null, null);
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    }

   private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
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
