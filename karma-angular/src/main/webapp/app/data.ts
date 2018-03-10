import { Space } from './entities/space';
import { Injectable } from '@angular/core';

@Injectable()
export class Data {

  public routingPath: any;
  public param: any;
  public heading: string;
  public all: boolean;
  public space: Space;

  constructor() {}

}
