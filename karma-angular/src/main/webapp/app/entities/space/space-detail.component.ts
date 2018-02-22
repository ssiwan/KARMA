import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Space } from './space.model';
import { SpaceService } from './space.service';

@Component({
    selector: 'jhi-space-detail',
    templateUrl: './space-detail.component.html'
})
export class SpaceDetailComponent implements OnInit, OnDestroy {

    space: Space;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private spaceService: SpaceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSpaces();
    }

    load(id) {
        this.spaceService.find(id)
            .subscribe((spaceResponse: HttpResponse<Space>) => {
                this.space = spaceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSpaces() {
        this.eventSubscriber = this.eventManager.subscribe(
            'spaceListModification',
            (response) => this.load(this.space.id)
        );
    }
}
