/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SentimentWebAppTestModule } from '../../../test.module';
import { SentimentDetailComponent } from 'app/entities/sentiment/sentiment-detail.component';
import { Sentiment } from 'app/shared/model/sentiment.model';

describe('Component Tests', () => {
    describe('Sentiment Management Detail Component', () => {
        let comp: SentimentDetailComponent;
        let fixture: ComponentFixture<SentimentDetailComponent>;
        const route = ({ data: of({ sentiment: new Sentiment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SentimentWebAppTestModule],
                declarations: [SentimentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SentimentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SentimentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sentiment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
