import { BaseEntity, User } from './../../shared';

export class TagHistory implements BaseEntity {
    constructor(
        public id?: number,
        public dateAccessed?: any,
        public tag?: BaseEntity,
        public user?: User,
    ) {
    }
}
