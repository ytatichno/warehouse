/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Good } from './Good';

export type Rack = {
    id?: number;
    addr?: string;
    good?: Good;
    number?: number;
};
export function rackToString(el:Rack){
    // const goodField =(!!el.good && "name" in el.good)?el.good.name:'null';
    return el&&el.addr?el.addr:'...';
}
