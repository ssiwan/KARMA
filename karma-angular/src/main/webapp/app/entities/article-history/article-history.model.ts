import { BaseEntity, User } from './../../shared';

export class ArticleHistory implements BaseEntity {
    constructor(
        public id?: number,
        public dateAccessed?: any,
        public article?: BaseEntity,
        public user?: User,
    ) {
    }
}
