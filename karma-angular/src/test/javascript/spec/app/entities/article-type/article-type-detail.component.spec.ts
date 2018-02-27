/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KarmaAngularTestModule } from '../../../test.module';
import { ArticleTypeDetailComponent } from '../../../../../../main/webapp/app/entities/article-type/article-type-detail.component';
import { ArticleTypeService } from '../../../../../../main/webapp/app/entities/article-type/article-type.service';
import { ArticleType } from '../../../../../../main/webapp/app/entities/article-type/article-type.model';

describe('Component Tests', () => {

    describe('ArticleType Management Detail Component', () => {
        let comp: ArticleTypeDetailComponent;
        let fixture: ComponentFixture<ArticleTypeDetailComponent>;
        let service: ArticleTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [ArticleTypeDetailComponent],
                providers: [
                    ArticleTypeService
                ]
            })
            .overrideTemplate(ArticleTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ArticleTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ArticleTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ArticleType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.articleType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
