import { BaseEntity, User } from './../../shared';

export class SpaceHistory implements BaseEntity {
    constructor(
        public id?: number,
        public dateAccessed?: any,
        public space?: BaseEntity,
        public user?: User,
    ) {
    }
}
