export interface ISentiment {
    id?: number;
    keyword?: string;
    sentiment?: string;
    platform?: string;
}

export class Sentiment implements ISentiment {
    constructor(public id?: number, public keyword?: string, public sentiment?: string, public platform?: string) {}
}
