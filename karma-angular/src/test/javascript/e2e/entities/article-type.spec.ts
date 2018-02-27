import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('ArticleType e2e test', () => {

    let navBarPage: NavBarPage;
    let articleTypeDialogPage: ArticleTypeDialogPage;
    let articleTypeComponentsPage: ArticleTypeComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ArticleTypes', () => {
        navBarPage.goToEntity('article-type');
        articleTypeComponentsPage = new ArticleTypeComponentsPage();
        expect(articleTypeComponentsPage.getTitle())
            .toMatch(/Article Types/);

    });

    it('should load create ArticleType dialog', () => {
        articleTypeComponentsPage.clickOnCreateButton();
        articleTypeDialogPage = new ArticleTypeDialogPage();
        expect(articleTypeDialogPage.getModalTitle())
            .toMatch(/Create or edit a Article Type/);
        articleTypeDialogPage.close();
    });

    it('should create and save ArticleTypes', () => {
        articleTypeComponentsPage.clickOnCreateButton();
        articleTypeDialogPage.setNameInput('name');
        expect(articleTypeDialogPage.getNameInput()).toMatch('name');
        articleTypeDialogPage.save();
        expect(articleTypeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ArticleTypeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-article-type div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class ArticleTypeDialogPage {
    modalTitle = element(by.css('h4#myArticleTypeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
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
