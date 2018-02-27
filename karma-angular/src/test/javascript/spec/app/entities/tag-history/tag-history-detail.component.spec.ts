/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KarmaAngularTestModule } from '../../../test.module';
import { TagHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/tag-history/tag-history-detail.component';
import { TagHistoryService } from '../../../../../../main/webapp/app/entities/tag-history/tag-history.service';
import { TagHistory } from '../../../../../../main/webapp/app/entities/tag-history/tag-history.model';

describe('Component Tests', () => {

    describe('TagHistory Management Detail Component', () => {
        let comp: TagHistoryDetailComponent;
        let fixture: ComponentFixture<TagHistoryDetailComponent>;
        let service: TagHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [TagHistoryDetailComponent],
                providers: [
                    TagHistoryService
                ]
            })
            .overrideTemplate(TagHistoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TagHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TagHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TagHistory(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tagHistory).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
