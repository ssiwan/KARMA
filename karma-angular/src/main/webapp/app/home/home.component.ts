import { Data } from '../data';
import {ArticleService} from '../entities/article/article.service';
import { Subscription } from 'rxjs/Subscription';
import {Component, OnInit, OnDestroy} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {Account, LoginModalService, Principal} from '../shared';

import {Article} from '../entities/article/article.model';
import { Space, SpaceService } from '../entities/space';
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
export class HomeComponent implements OnInit, OnDestroy {
  account: any;
  modalRef: NgbModalRef;
  searchArticles: Article[];
  searchString: string;
  recentArticles: Article[];
  recentTags: Tag[];
  recentSpaces: Space[];
  frequentArticles: Article[];
  articles: Article[];
  articlesForSpace: Article[];
  allSpaces: Space[];
  eventSubscriber: Subscription;

  constructor(
    private principal: Principal,
    private loginModalService: LoginModalService,
    private articleService: ArticleService,
    private eventManager: JhiEventManager,
    private jhiAlertService: JhiAlertService,
    private tagService: TagService,
    private spaceService: SpaceService,
    private data: Data,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.principal.identity().then((account) => {
      this.account = account;
      if (this.account != null) {
        this.getRecentArticles();
        this.getRecentTags();
        this.getFrequentArticles();
        this.getRecentSpaces();
      }
    });
    this.registerAuthenticationSuccess();
    this.registerChangeInArticles();
    this.data.space = null;

    this.searchArticles = [];
    this.searchString = '';
    this.recentArticles = [];
    this.recentTags = [];
    this.recentSpaces = [];
    this.frequentArticles = [];
    this.articles = [];
    this.articlesForSpace = [];
    this.allSpaces = [];
  }

  ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

  registerAuthenticationSuccess() {
    this.eventManager.subscribe('authenticationSuccess', (message) => {
      this.principal.identity().then((account) => {
        this.account = account;
        this.data.space = null;
        if (this.account != null) {
          this.getRecentInfo();
        }
      });
    });
  }

  private getRecentInfo() {
    this.recentArticles = [];
          this.recentTags = [];
          this.recentSpaces = [];
          this.frequentArticles = [];
          this.getRecentArticles();
          this.getRecentTags();
          this.getRecentSpaces();
          this.getFrequentArticles();
  }

  isAuthenticated() {
    return this.principal.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  searchTitle(searchString: string) {
    this.data.routingPath = 'searchTitles';
    this.data.param = searchString;
    this.data.heading = 'Searched by Keyword: ' + searchString;
    this.data.all = false;
    this.data.space = null;
    this.router.navigate(['/article']);
  }

  getArticlesBySpace(space: Space) {
      this.data.routingPath = 'searchSpace';
      this.data.param = space.id;
      this.data.heading = 'Search by Knowledge Area: ' + space.name;
      this.data.all = false;
      this.data.space = space;
      this.router.navigate(['/article']);
  }

  getRecentArticles() {
    this.articleService.findRecentlyAccessed(this.account.id).subscribe(
      (res: HttpResponse<Article[]>) => this.recentAccessedOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  getFrequentArticles() {
    this.articleService.findFrequentlyAccessed().subscribe(
      (res: HttpResponse<Article[]>) => this.frequentAccessedOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  getRecentTags() {
    this.tagService.findRecentlyAccessed(this.account.id).subscribe(
      (res: HttpResponse<Tag[]>) => this.recentTagsOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  getAllArticles() {
    this.data.routingPath = '';
    this.data.param = null;
    this.data.heading = 'All';
    this.data.all = true;
    this.data.space = null;
    this.router.navigate(['/article']);
  }

  getRecentSpaces() {
    this.spaceService.findRecentlyAccessed(this.account.id).subscribe(
      (res: HttpResponse<Space[]>) => this.recentSpacesOnSuccess(res.body, res.headers),
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  getArticleByTag(tagId, tagName) {
    this.data.routingPath = 'tag';
    this.data.param = tagId;
    this.data.heading = 'Search by Tag: ' + tagName;
    this.data.all = false;
    this.data.space = null;
    this.router.navigate(['/article']);
  }

  registerChangeInArticles() {
        this.eventSubscriber = this.eventManager.subscribe('articleListModification', (response) => this.reset());
    }

  private reset() {
    this.getRecentInfo();
  }

  private recentAccessedOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.recentArticles.push(data[i]);
    }
  }

  private frequentAccessedOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.frequentArticles.push(data[i]);
    }
  }

  private recentTagsOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.recentTags.push(data[i]);
    }
  }

  private recentSpacesOnSuccess(data, headers) {
    for (let i = 0; i < data.length; i++) {
      this.recentSpaces.push(data[i]);
    }
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }
}
