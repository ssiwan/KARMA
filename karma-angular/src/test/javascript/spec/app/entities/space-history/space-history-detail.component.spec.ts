/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KarmaAngularTestModule } from '../../../test.module';
import { SpaceHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/space-history/space-history-detail.component';
import { SpaceHistoryService } from '../../../../../../main/webapp/app/entities/space-history/space-history.service';
import { SpaceHistory } from '../../../../../../main/webapp/app/entities/space-history/space-history.model';

describe('Component Tests', () => {

    describe('SpaceHistory Management Detail Component', () => {
        let comp: SpaceHistoryDetailComponent;
        let fixture: ComponentFixture<SpaceHistoryDetailComponent>;
        let service: SpaceHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [SpaceHistoryDetailComponent],
                providers: [
                    SpaceHistoryService
                ]
            })
            .overrideTemplate(SpaceHistoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpaceHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpaceHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SpaceHistory(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.spaceHistory).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
