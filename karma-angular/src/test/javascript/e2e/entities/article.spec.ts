import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Article e2e test', () => {

    let navBarPage: NavBarPage;
    let articleDialogPage: ArticleDialogPage;
    let articleComponentsPage: ArticleComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Articles', () => {
        navBarPage.goToEntity('article');
        articleComponentsPage = new ArticleComponentsPage();
        expect(articleComponentsPage.getTitle())
            .toMatch(/Articles/);

    });

    it('should load create Article dialog', () => {
        articleComponentsPage.clickOnCreateButton();
        articleDialogPage = new ArticleDialogPage();
        expect(articleDialogPage.getModalTitle())
            .toMatch(/Create or edit a Article/);
        articleDialogPage.close();
    });

    it('should create and save Articles', () => {
        articleComponentsPage.clickOnCreateButton();
        articleDialogPage.setTitleInput('title');
        expect(articleDialogPage.getTitleInput()).toMatch('title');
        articleDialogPage.setContentInput('content');
        expect(articleDialogPage.getContentInput()).toMatch('content');
        articleDialogPage.setDateInput(12310020012301);
        expect(articleDialogPage.getDateInput()).toMatch('2001-12-31T02:30');
        articleDialogPage.spaceSelectLastOption();
        articleDialogPage.userSelectLastOption();
        // articleDialogPage.tagSelectLastOption();
        // articleDialogPage.articleTypeSelectLastOption();
        articleDialogPage.save();
        expect(articleDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ArticleComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-article div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ArticleDialogPage {
    modalTitle = element(by.css('h4#myArticleLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    contentInput = element(by.css('textarea#field_content'));
    dateInput = element(by.css('input#field_date'));
    spaceSelect = element(by.css('select#field_space'));
    userSelect = element(by.css('select#field_user'));
    tagSelect = element(by.css('select#field_tag'));
    articleTypeSelect = element(by.css('select#field_articleType'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTitleInput = function(title) {
        this.titleInput.sendKeys(title);
    };

    getTitleInput = function() {
        return this.titleInput.getAttribute('value');
    };

    setContentInput = function(content) {
        this.contentInput.sendKeys(content);
    };

    getContentInput = function() {
        return this.contentInput.getAttribute('value');
    };

    setDateInput = function(date) {
        this.dateInput.sendKeys(date);
    };

    getDateInput = function() {
        return this.dateInput.getAttribute('value');
    };

    spaceSelectLastOption = function() {
        this.spaceSelect.all(by.tagName('option')).last().click();
    };

    spaceSelectOption = function(option) {
        this.spaceSelect.sendKeys(option);
    };

    getSpaceSelect = function() {
        return this.spaceSelect;
    };

    getSpaceSelectedOption = function() {
        return this.spaceSelect.element(by.css('option:checked')).getText();
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

    tagSelectLastOption = function() {
        this.tagSelect.all(by.tagName('option')).last().click();
    };

    tagSelectOption = function(option) {
        this.tagSelect.sendKeys(option);
    };

    getTagSelect = function() {
        return this.tagSelect;
    };

    getTagSelectedOption = function() {
        return this.tagSelect.element(by.css('option:checked')).getText();
    };

    articleTypeSelectLastOption = function() {
        this.articleTypeSelect.all(by.tagName('option')).last().click();
    };

    articleTypeSelectOption = function(option) {
        this.articleTypeSelect.sendKeys(option);
    };

    getArticleTypeSelect = function() {
        return this.articleTypeSelect;
    };

    getArticleTypeSelectedOption = function() {
        return this.articleTypeSelect.element(by.css('option:checked')).getText();
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
