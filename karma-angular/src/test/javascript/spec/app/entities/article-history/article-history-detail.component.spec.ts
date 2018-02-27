/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KarmaAngularTestModule } from '../../../test.module';
import { ArticleHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/article-history/article-history-detail.component';
import { ArticleHistoryService } from '../../../../../../main/webapp/app/entities/article-history/article-history.service';
import { ArticleHistory } from '../../../../../../main/webapp/app/entities/article-history/article-history.model';

describe('Component Tests', () => {

    describe('ArticleHistory Management Detail Component', () => {
        let comp: ArticleHistoryDetailComponent;
        let fixture: ComponentFixture<ArticleHistoryDetailComponent>;
        let service: ArticleHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [ArticleHistoryDetailComponent],
                providers: [
                    ArticleHistoryService
                ]
            })
            .overrideTemplate(ArticleHistoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ArticleHistory(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.articleHistory).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
