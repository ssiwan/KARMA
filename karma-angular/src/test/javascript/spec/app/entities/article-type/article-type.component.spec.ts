/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KarmaAngularTestModule } from '../../../test.module';
import { ArticleTypeComponent } from '../../../../../../main/webapp/app/entities/article-type/article-type.component';
import { ArticleTypeService } from '../../../../../../main/webapp/app/entities/article-type/article-type.service';
import { ArticleType } from '../../../../../../main/webapp/app/entities/article-type/article-type.model';

describe('Component Tests', () => {

    describe('ArticleType Management Component', () => {
        let comp: ArticleTypeComponent;
        let fixture: ComponentFixture<ArticleTypeComponent>;
        let service: ArticleTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [ArticleTypeComponent],
                providers: [
                    ArticleTypeService
                ]
            })
            .overrideTemplate(ArticleTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ArticleType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.articleTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
