/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KarmaAngularTestModule } from '../../../test.module';
import { TagHistoryComponent } from '../../../../../../main/webapp/app/entities/tag-history/tag-history.component';
import { TagHistoryService } from '../../../../../../main/webapp/app/entities/tag-history/tag-history.service';
import { TagHistory } from '../../../../../../main/webapp/app/entities/tag-history/tag-history.model';

describe('Component Tests', () => {

    describe('TagHistory Management Component', () => {
        let comp: TagHistoryComponent;
        let fixture: ComponentFixture<TagHistoryComponent>;
        let service: TagHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [TagHistoryComponent],
                providers: [
                    TagHistoryService
                ]
            })
            .overrideTemplate(TagHistoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TagHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TagHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TagHistory(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tagHistories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
