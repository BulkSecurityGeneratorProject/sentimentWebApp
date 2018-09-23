import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SentimentWebAppSharedModule } from 'app/shared';
import {
    SentimentComponent,
    SentimentDetailComponent,
    SentimentUpdateComponent,
    SentimentDeletePopupComponent,
    SentimentDeleteDialogComponent,
    sentimentRoute,
    sentimentPopupRoute
} from './';

const ENTITY_STATES = [...sentimentRoute, ...sentimentPopupRoute];

@NgModule({
    imports: [SentimentWebAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SentimentComponent,
        SentimentDetailComponent,
        SentimentUpdateComponent,
        SentimentDeleteDialogComponent,
        SentimentDeletePopupComponent
    ],
    entryComponents: [SentimentComponent, SentimentUpdateComponent, SentimentDeleteDialogComponent, SentimentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SentimentWebAppSentimentModule {}
