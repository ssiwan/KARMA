import { Data } from '../data';
import {ArticleService} from '../entities/article/article.service';
import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {Account, LoginModalService, Principal} from '../shared';

import {Article} from '../entities/article/article.model';
import {TagService, Tag} from '../entities/tag';
import {HttpErrorResponse} from '@angular/common/http';
import {HttpResponse} from '@angular/common/http';
import { Router } from '@angular/router';

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
  searchArticles: Article[];
  searchString: string;
  recentArticles: Article[];
  recentTags: Tag[];

  constructor(
    private principal: Principal,
    private loginModalService: LoginModalService,
    private articleService: ArticleService,
    private eventManager: JhiEventManager,
    private jhiAlertService: JhiAlertService,
    private tagService: TagService,
    private data: Data,
    private router: Router
  ) {

  }

  ngOnInit() {
    this.principal.identity().then((account) => {
      this.account = account;
      if (this.account != null) {
        this.getRecentArtices();
        this.getRecentTags();
      }
    });
    this.registerAuthenticationSuccess();

    this.searchArticles = [];
    this.searchString = '';
    this.recentArticles = [];
    this.recentTags = [];
  }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', (message) => {
      this.principal.identity().then((account) => {
        this.account = account;
        if (this.account != null) {
          this.recentArticles = [];
          this.recentTags = [];
          this.getRecentArtices();
          this.getRecentTags();
        }
      });
    });
  }

  isAuthenticated() {
    return this.principal.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  searchTitle(searchString: string) {
    this.articleService.search(searchString).subscribe(
      (res: HttpResponse<Article[]>) => this.searchTitleOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  getRecentArtices() {
    this.articleService.findRecentlyAccessed(this.account.id).subscribe(
      (res: HttpResponse<Article[]>) => this.recentAccessedOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  getRecentTags() {
    this.tagService.findRecentlyAccessed(this.account.id).subscribe(
      (res: HttpResponse<Tag[]>) => this.recentTagsOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private searchTitleOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.searchArticles.push(data[i]);
    }
    this.data.storage = this.searchArticles;
    this.router.navigate(['/article-list']);
  }

  private recentAccessedOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.recentArticles.push(data[i]);
    }
  }

  private recentTagsOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.recentTags.push(data[i]);
    }
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
}
