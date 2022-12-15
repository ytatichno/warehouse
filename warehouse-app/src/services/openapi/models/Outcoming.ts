/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Good } from './Good';
import type { Rack } from './Rack';
import type { Usercard } from './Usercard';

export type Outcoming = {
    id?: number;
    usercard?: Usercard;
    datetime?: string;
    rack?: Rack;
    good?: Good;
    number?: number;
    satisfied?: boolean;
};
