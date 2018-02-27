import { BaseEntity } from './../../shared';

export class ArticleType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public articles?: BaseEntity[],
    ) {
    }
}
