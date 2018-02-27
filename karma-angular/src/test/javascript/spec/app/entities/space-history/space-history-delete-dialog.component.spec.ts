/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KarmaAngularTestModule } from '../../../test.module';
import { SpaceHistoryDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/space-history/space-history-delete-dialog.component';
import { SpaceHistoryService } from '../../../../../../main/webapp/app/entities/space-history/space-history.service';

describe('Component Tests', () => {

    describe('SpaceHistory Management Delete Component', () => {
        let comp: SpaceHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<SpaceHistoryDeleteDialogComponent>;
        let service: SpaceHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [SpaceHistoryDeleteDialogComponent],
                providers: [
                    SpaceHistoryService
                ]
            })
            .overrideTemplate(SpaceHistoryDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpaceHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpaceHistoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
