import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Router } from '@angular/router';

import { Data } from '../../data';
import {Article} from '../article/article.model';
import { Space } from './space.model';
import { SpaceService } from './space.service';
import {ArticleService} from '../article/article.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-space',
    templateUrl: './space.component.html'
})
export class SpaceComponent implements OnInit, OnDestroy {
spaces: Space[];
    currentAccount: any;
    eventSubscriber: Subscription;
    articlesForSpace: Article[];

    constructor(
        private spaceService: SpaceService,
        private articleService: ArticleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private data: Data,
        private router: Router,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.spaceService.query().subscribe(
            (res: HttpResponse<Space[]>) => {
                this.spaces = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSpaces();
        this.articlesForSpace = [];
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Space) {
        return item.id;
    }
    registerChangeInSpaces() {
        this.eventSubscriber = this.eventManager.subscribe('spaceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    getArticlesBySpace(space: Space) {
      this.data.routingPath = 'searchSpace';
      this.data.param = space.id;
      this.data.heading = 'Search by Knowledge Area: ' + space.name;
      this.data.all = false;
      this.data.space = space;
      this.router.navigate(['/article']);
    }

}
