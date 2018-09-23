import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISentiment } from 'app/shared/model/sentiment.model';
import { SentimentService } from './sentiment.service';

@Component({
    selector: 'jhi-sentiment-update',
    templateUrl: './sentiment-update.component.html'
})
export class SentimentUpdateComponent implements OnInit {
    private _sentiment: ISentiment;
    isSaving: boolean;

    constructor(private sentimentService: SentimentService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sentiment }) => {
            this.sentiment = sentiment;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sentiment.id !== undefined) {
            this.subscribeToSaveResponse(this.sentimentService.update(this.sentiment));
        } else {
            this.subscribeToSaveResponse(this.sentimentService.create(this.sentiment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISentiment>>) {
        result.subscribe((res: HttpResponse<ISentiment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get sentiment() {
        return this._sentiment;
    }

    set sentiment(sentiment: ISentiment) {
        this._sentiment = sentiment;
    }
}
