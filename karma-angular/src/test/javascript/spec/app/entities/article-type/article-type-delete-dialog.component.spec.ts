/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { KarmaAngularTestModule } from '../../../test.module';
import { ArticleTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/article-type/article-type-delete-dialog.component';
import { ArticleTypeService } from '../../../../../../main/webapp/app/entities/article-type/article-type.service';

describe('Component Tests', () => {

    describe('ArticleType Management Delete Component', () => {
        let comp: ArticleTypeDeleteDialogComponent;
        let fixture: ComponentFixture<ArticleTypeDeleteDialogComponent>;
        let service: ArticleTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [ArticleTypeDeleteDialogComponent],
                providers: [
                    ArticleTypeService
                ]
            })
            .overrideTemplate(ArticleTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleTypeService);
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
