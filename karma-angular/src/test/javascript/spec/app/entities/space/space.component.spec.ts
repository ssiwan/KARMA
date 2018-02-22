/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { KarmaAngularTestModule } from '../../../test.module';
import { SpaceComponent } from '../../../../../../main/webapp/app/entities/space/space.component';
import { SpaceService } from '../../../../../../main/webapp/app/entities/space/space.service';
import { Space } from '../../../../../../main/webapp/app/entities/space/space.model';

describe('Component Tests', () => {

    describe('Space Management Component', () => {
        let comp: SpaceComponent;
        let fixture: ComponentFixture<SpaceComponent>;
        let service: SpaceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KarmaAngularTestModule],
                declarations: [SpaceComponent],
                providers: [
                    SpaceService
                ]
            })
            .overrideTemplate(SpaceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SpaceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpaceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Space(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.spaces[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
