/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SentimentWebAppTestModule } from '../../../test.module';
import { SentimentUpdateComponent } from 'app/entities/sentiment/sentiment-update.component';
import { SentimentService } from 'app/entities/sentiment/sentiment.service';
import { Sentiment } from 'app/shared/model/sentiment.model';

describe('Component Tests', () => {
    describe('Sentiment Management Update Component', () => {
        let comp: SentimentUpdateComponent;
        let fixture: ComponentFixture<SentimentUpdateComponent>;
        let service: SentimentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SentimentWebAppTestModule],
                declarations: [SentimentUpdateComponent]
            })
                .overrideTemplate(SentimentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SentimentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SentimentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sentiment(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sentiment = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sentiment();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sentiment = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
