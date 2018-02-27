import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('SpaceHistory e2e test', () => {

    let navBarPage: NavBarPage;
    let spaceHistoryDialogPage: SpaceHistoryDialogPage;
    let spaceHistoryComponentsPage: SpaceHistoryComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load SpaceHistories', () => {
        navBarPage.goToEntity('space-history');
        spaceHistoryComponentsPage = new SpaceHistoryComponentsPage();
        expect(spaceHistoryComponentsPage.getTitle())
            .toMatch(/Space Histories/);

    });

    it('should load create SpaceHistory dialog', () => {
        spaceHistoryComponentsPage.clickOnCreateButton();
        spaceHistoryDialogPage = new SpaceHistoryDialogPage();
        expect(spaceHistoryDialogPage.getModalTitle())
            .toMatch(/Create or edit a Space History/);
        spaceHistoryDialogPage.close();
    });

    it('should create and save SpaceHistories', () => {
        spaceHistoryComponentsPage.clickOnCreateButton();
        spaceHistoryDialogPage.setDateAccessedInput(12310020012301);
        expect(spaceHistoryDialogPage.getDateAccessedInput()).toMatch('2001-12-31T02:30');
        spaceHistoryDialogPage.spaceSelectLastOption();
        spaceHistoryDialogPage.userSelectLastOption();
        spaceHistoryDialogPage.save();
        expect(spaceHistoryDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SpaceHistoryComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-space-history div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SpaceHistoryDialogPage {
    modalTitle = element(by.css('h4#mySpaceHistoryLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    dateAccessedInput = element(by.css('input#field_dateAccessed'));
    spaceSelect = element(by.css('select#field_space'));
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
