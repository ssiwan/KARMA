import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Space e2e test', () => {

    let navBarPage: NavBarPage;
    let spaceDialogPage: SpaceDialogPage;
    let spaceComponentsPage: SpaceComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Spaces', () => {
        navBarPage.goToEntity('space');
        spaceComponentsPage = new SpaceComponentsPage();
        expect(spaceComponentsPage.getTitle())
            .toMatch(/Spaces/);

    });

    it('should load create Space dialog', () => {
        spaceComponentsPage.clickOnCreateButton();
        spaceDialogPage = new SpaceDialogPage();
        expect(spaceDialogPage.getModalTitle())
            .toMatch(/Create or edit a Space/);
        spaceDialogPage.close();
    });

    it('should create and save Spaces', () => {
        spaceComponentsPage.clickOnCreateButton();
        spaceDialogPage.setNameInput('name');
        expect(spaceDialogPage.getNameInput()).toMatch('name');
        spaceDialogPage.setHandleInput('handle');
        expect(spaceDialogPage.getHandleInput()).toMatch('handle');
        spaceDialogPage.userSelectLastOption();
        spaceDialogPage.save();
        expect(spaceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class SpaceComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-space div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class SpaceDialogPage {
    modalTitle = element(by.css('h4#mySpaceLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    handleInput = element(by.css('input#field_handle'));
    userSelect = element(by.css('select#field_user'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setHandleInput = function(handle) {
        this.handleInput.sendKeys(handle);
    };

    getHandleInput = function() {
        return this.handleInput.getAttribute('value');
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
