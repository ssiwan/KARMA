import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ArticleHistory e2e test', () => {

    let navBarPage: NavBarPage;
    let articleHistoryDialogPage: ArticleHistoryDialogPage;
    let articleHistoryComponentsPage: ArticleHistoryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ArticleHistories', () => {
        navBarPage.goToEntity('article-history');
        articleHistoryComponentsPage = new ArticleHistoryComponentsPage();
        expect(articleHistoryComponentsPage.getTitle())
            .toMatch(/Article Histories/);

    });

    it('should load create ArticleHistory dialog', () => {
        articleHistoryComponentsPage.clickOnCreateButton();
        articleHistoryDialogPage = new ArticleHistoryDialogPage();
        expect(articleHistoryDialogPage.getModalTitle())
            .toMatch(/Create or edit a Article History/);
        articleHistoryDialogPage.close();
    });

    it('should create and save ArticleHistories', () => {
        articleHistoryComponentsPage.clickOnCreateButton();
        articleHistoryDialogPage.setDateAccessedInput(12310020012301);
        expect(articleHistoryDialogPage.getDateAccessedInput()).toMatch('2001-12-31T02:30');
        articleHistoryDialogPage.articleSelectLastOption();
        articleHistoryDialogPage.userSelectLastOption();
        articleHistoryDialogPage.save();
        expect(articleHistoryDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ArticleHistoryComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-article-history div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ArticleHistoryDialogPage {
    modalTitle = element(by.css('h4#myArticleHistoryLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    dateAccessedInput = element(by.css('input#field_dateAccessed'));
    articleSelect = element(by.css('select#field_article'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setDateAccessedInput = function(dateAccessed) {
        this.dateAccessedInput.sendKeys(dateAccessed);
    };

    getDateAccessedInput = function() {
        return this.dateAccessedInput.getAttribute('value');
    };

    articleSelectLastOption = function() {
        this.articleSelect.all(by.tagName('option')).last().click();
    };

    articleSelectOption = function(option) {
        this.articleSelect.sendKeys(option);
    };

    getArticleSelect = function() {
        return this.articleSelect;
    };

    getArticleSelectedOption = function() {
        return this.articleSelect.element(by.css('option:checked')).getText();
    };

    userSelectLastOption = function() {
        this.userSelect.all(by.tagName('option')).last().click();
    };

    userSelectOption = function(option) {
        this.userSelect.sendKeys(option);
    };

    getUserSelect = function() {
        return this.userSelect;
    };

    getUserSelectedOption = function() {
        return this.userSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
