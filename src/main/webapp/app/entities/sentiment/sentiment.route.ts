import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sentiment } from 'app/shared/model/sentiment.model';
import { SentimentService } from './sentiment.service';
import { SentimentComponent } from './sentiment.component';
import { SentimentDetailComponent } from './sentiment-detail.component';
import { SentimentUpdateComponent } from './sentiment-update.component';
import { SentimentDeletePopupComponent } from './sentiment-delete-dialog.component';
import { ISentiment } from 'app/shared/model/sentiment.model';

@Injectable({ providedIn: 'root' })
export class SentimentResolve implements Resolve<ISentiment> {
    constructor(private service: SentimentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sentiment: HttpResponse<Sentiment>) => sentiment.body));
        }
        return of(new Sentiment());
    }
}

export const sentimentRoute: Routes = [
    {
        path: 'sentiment',
        component: SentimentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sentimentWebApp.sentiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sentiment/:id/view',
        component: SentimentDetailComponent,
        resolve: {
            sentiment: SentimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sentimentWebApp.sentiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sentiment/new',
        component: SentimentUpdateComponent,
        resolve: {
            sentiment: SentimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sentimentWebApp.sentiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sentiment/:id/edit',
        component: SentimentUpdateComponent,
        resolve: {
            sentiment: SentimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sentimentWebApp.sentiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sentimentPopupRoute: Routes = [
    {
        path: 'sentiment/:id/delete',
        component: SentimentDeletePopupComponent,
        resolve: {
            sentiment: SentimentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sentimentWebApp.sentiment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
