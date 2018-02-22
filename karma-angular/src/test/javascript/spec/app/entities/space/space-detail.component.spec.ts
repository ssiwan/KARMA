/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { KarmaAngularTestModule } from '../../../test.module';
import { SpaceDetailComponent } from '../../../../../../main/webapp/app/entities/space/space-detail.component';
import { SpaceService } from '../../../../../../main/webapp/app/entities/space/space.service';
import { Space } from '../../../../../../main/webapp/app/entities/space/space.model';

describe('Component Tests', () => {

    describe('Space Management Detail Component', () => {
        let comp: SpaceDetailComponent;
        let fixture: ComponentFixture<SpaceDetailComponent>;
        let service: SpaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [SpaceDetailComponent],
                providers: [
                    SpaceService
                ]
            })
            .overrideTemplate(SpaceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpaceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Space(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.space).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
