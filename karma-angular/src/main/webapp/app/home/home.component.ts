import {Article} from '../entities/article';
import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {Account, LoginModalService, Principal} from '../shared';
import {ArticleService} from '../entities/article/article.service';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpResponse} from '@angular/common/http';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: [
    'home.css'
  ]

})
export class HomeComponent implements OnInit {
  account: any;
  modalRef: NgbModalRef;
  recentArticles: Article[];

  constructor(
    private principal: Principal,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private articleService: ArticleService,
    private jhiAlertService: JhiAlertService,
  ) {
    this.recentArticles = [];
  }

  ngOnInit() {
    this.principal.identity().then((account) => {
      this.account = account;
      this.getRecentArtices();
    });
    this.registerAuthenticationSuccess();
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', (message) => {
      this.principal.identity().then((account) => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.principal.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  getRecentArtices() {
    this.articleService.findRecentlyAccessed(this.account.id).subscribe(
      (res: HttpResponse<Article[]>) => this.recentAccessedOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private recentAccessedOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.recentArticles.push(data[i]);
    }
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
}
