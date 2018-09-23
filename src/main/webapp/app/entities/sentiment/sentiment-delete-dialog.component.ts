import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISentiment } from 'app/shared/model/sentiment.model';
import { SentimentService } from './sentiment.service';

@Component({
    selector: 'jhi-sentiment-delete-dialog',
    templateUrl: './sentiment-delete-dialog.component.html'
})
export class SentimentDeleteDialogComponent {
    sentiment: ISentiment;

    constructor(private sentimentService: SentimentService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sentimentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sentimentListModification',
                content: 'Deleted an sentiment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sentiment-delete-popup',
    template: ''
})
export class SentimentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sentiment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SentimentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sentiment = sentiment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
