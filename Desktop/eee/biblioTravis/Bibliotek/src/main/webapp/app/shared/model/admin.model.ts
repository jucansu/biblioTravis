export interface IAdmin {
    id?: number;
}

export class Admin implements IAdmin {
    constructor(public id?: number) {}
}
