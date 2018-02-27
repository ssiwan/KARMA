/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KarmaAngularTestModule } from '../../../test.module';
import { SpaceHistoryComponent } from '../../../../../../main/webapp/app/entities/space-history/space-history.component';
import { SpaceHistoryService } from '../../../../../../main/webapp/app/entities/space-history/space-history.service';
import { SpaceHistory } from '../../../../../../main/webapp/app/entities/space-history/space-history.model';

describe('Component Tests', () => {

    describe('SpaceHistory Management Component', () => {
        let comp: SpaceHistoryComponent;
        let fixture: ComponentFixture<SpaceHistoryComponent>;
        let service: SpaceHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [SpaceHistoryComponent],
                providers: [
                    SpaceHistoryService
                ]
            })
            .overrideTemplate(SpaceHistoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpaceHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpaceHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SpaceHistory(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.spaceHistories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
