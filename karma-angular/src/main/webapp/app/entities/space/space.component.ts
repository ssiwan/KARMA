import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Space } from './space.model';
import { SpaceService } from './space.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-space',
    templateUrl: './space.component.html'
})
export class SpaceComponent implements OnInit, OnDestroy {
spaces: Space[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private spaceService: SpaceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
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
}
