import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('TagHistory e2e test', () => {

    let navBarPage: NavBarPage;
    let tagHistoryDialogPage: TagHistoryDialogPage;
    let tagHistoryComponentsPage: TagHistoryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load TagHistories', () => {
        navBarPage.goToEntity('tag-history');
        tagHistoryComponentsPage = new TagHistoryComponentsPage();
        expect(tagHistoryComponentsPage.getTitle())
            .toMatch(/Tag Histories/);

    });

    it('should load create TagHistory dialog', () => {
        tagHistoryComponentsPage.clickOnCreateButton();
        tagHistoryDialogPage = new TagHistoryDialogPage();
        expect(tagHistoryDialogPage.getModalTitle())
            .toMatch(/Create or edit a Tag History/);
        tagHistoryDialogPage.close();
    });

    it('should create and save TagHistories', () => {
        tagHistoryComponentsPage.clickOnCreateButton();
        tagHistoryDialogPage.setDateAccessedInput(12310020012301);
        expect(tagHistoryDialogPage.getDateAccessedInput()).toMatch('2001-12-31T02:30');
        tagHistoryDialogPage.tagSelectLastOption();
        tagHistoryDialogPage.userSelectLastOption();
        tagHistoryDialogPage.save();
        expect(tagHistoryDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TagHistoryComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-tag-history div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class TagHistoryDialogPage {
    modalTitle = element(by.css('h4#myTagHistoryLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    dateAccessedInput = element(by.css('input#field_dateAccessed'));
    tagSelect = element(by.css('select#field_tag'));
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
