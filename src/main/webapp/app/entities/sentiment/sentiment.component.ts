import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISentiment } from 'app/shared/model/sentiment.model';
import { Principal } from 'app/core';
import { SentimentService } from './sentiment.service';

@Component({
    selector: 'jhi-sentiment',
    templateUrl: './sentiment.component.html'
})
export class SentimentComponent implements OnInit, OnDestroy {
    sentiments: ISentiment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sentimentService: SentimentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.sentimentService.query().subscribe(
            (res: HttpResponse<ISentiment[]>) => {
                this.sentiments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSentiments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISentiment) {
        return item.id;
    }

    registerChangeInSentiments() {
        this.eventSubscriber = this.eventManager.subscribe('sentimentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
