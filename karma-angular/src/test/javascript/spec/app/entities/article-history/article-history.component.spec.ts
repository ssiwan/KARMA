/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KarmaAngularTestModule } from '../../../test.module';
import { ArticleHistoryComponent } from '../../../../../../main/webapp/app/entities/article-history/article-history.component';
import { ArticleHistoryService } from '../../../../../../main/webapp/app/entities/article-history/article-history.service';
import { ArticleHistory } from '../../../../../../main/webapp/app/entities/article-history/article-history.model';

describe('Component Tests', () => {

    describe('ArticleHistory Management Component', () => {
        let comp: ArticleHistoryComponent;
        let fixture: ComponentFixture<ArticleHistoryComponent>;
        let service: ArticleHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [ArticleHistoryComponent],
                providers: [
                    ArticleHistoryService
                ]
            })
            .overrideTemplate(ArticleHistoryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleHistoryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleHistoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ArticleHistory(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.articleHistories[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
