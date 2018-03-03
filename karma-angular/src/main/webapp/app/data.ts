import { Injectable } from '@angular/core';

@Injectable()
export class Data {

  public storage: any;

  constructor() {}

   setData(data) {
    this.storage = data;
  }
  getData() {
    return this.storage;
  }

}
