import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISentiment } from 'app/shared/model/sentiment.model';

type EntityResponseType = HttpResponse<ISentiment>;
type EntityArrayResponseType = HttpResponse<ISentiment[]>;

@Injectable({ providedIn: 'root' })
export class SentimentService {
    private resourceUrl = SERVER_API_URL + 'api/sentiments';

    constructor(private http: HttpClient) {}

    create(sentiment: ISentiment): Observable<EntityResponseType> {
        return this.http.post<ISentiment>(this.resourceUrl, sentiment, { observe: 'response' });
    }

    update(sentiment: ISentiment): Observable<EntityResponseType> {
        return this.http.put<ISentiment>(this.resourceUrl, sentiment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISentiment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISentiment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
