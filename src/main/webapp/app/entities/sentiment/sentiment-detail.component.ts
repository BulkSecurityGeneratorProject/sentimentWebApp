import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISentiment } from 'app/shared/model/sentiment.model';

@Component({
    selector: 'jhi-sentiment-detail',
    templateUrl: './sentiment-detail.component.html'
})
export class SentimentDetailComponent implements OnInit {
    sentiment: ISentiment;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sentiment }) => {
            this.sentiment = sentiment;
        });
    }

    previousState() {
        window.history.back();
    }
}
