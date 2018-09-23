/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SentimentWebAppTestModule } from '../../../test.module';
import { SentimentComponent } from 'app/entities/sentiment/sentiment.component';
import { SentimentService } from 'app/entities/sentiment/sentiment.service';
import { Sentiment } from 'app/shared/model/sentiment.model';

describe('Component Tests', () => {
    describe('Sentiment Management Component', () => {
        let comp: SentimentComponent;
        let fixture: ComponentFixture<SentimentComponent>;
        let service: SentimentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SentimentWebAppTestModule],
                declarations: [SentimentComponent],
                providers: []
            })
                .overrideTemplate(SentimentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SentimentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SentimentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Sentiment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sentiments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
