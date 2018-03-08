import { ArticleService } from '../../entities/article/article.service';
import { SpaceService } from '../../entities/space/space.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { User, UserService } from '../../shared';
import { UserModalService } from './user-modal.service';
import { HttpResponse } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-user-mgmt-delete-dialog',
    templateUrl: './user-management-delete-dialog.component.html'
})
export class UserMgmtDeleteDialogComponent {

    user: User;
    articleCount: number;
    spaceCount: number;

    constructor(
        private userService: UserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager,
        private spaceService: SpaceService,
        private articleService: ArticleService,
        private jhiAlertService: JhiAlertService
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(login) {
       this.userService.find(login).subscribe((response) => {
            this.user = response.body;
        });

      this.articleService.countByUserId(this.user.id).subscribe(
        (res: HttpResponse<number>) => {
          this.articleCount = res.body;

          if (this.articleCount === 0) {
            this.spaceService.countByUserId(this.user.id).subscribe(
              (res2: HttpResponse<number>) => {
                this.spaceCount = res2.body;
                if (this.spaceCount === 0) {

                  this.userService.delete(login).subscribe((response) => {
                    this.eventManager.broadcast({
                      name: 'userListModification',
                      content: 'Deleted a user'
                    });
                    this.activeModal.dismiss(true);
                  });
                  this.activeModal.dismiss(true);
                } else {
                    this.activeModal.dismiss(true);
                    this.jhiAlertService.error('Cannot Delete, associated Knowledge Areas', null, null);
                  }
              },
              (res2: HttpErrorResponse) => this.onError(res2.message)
            );

          } else {
            this.activeModal.dismiss(true);
            this.jhiAlertService.error('Cannot Delete, associated Knowledge Articles', null, null);
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
    selector: 'jhi-user-delete-dialog',
    template: ''
})
export class UserDeleteDialogComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userModalService: UserModalService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.userModalService.open(UserMgmtDeleteDialogComponent as Component, params['login']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
