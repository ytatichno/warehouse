/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import { getOriginalNode } from "typescript";
import { IListProps } from "../../../components/UI/List/IListProps";

export type Good = {
    id?: number;
    name?: string;
    descr?: string;
    totalnumber?: number;
};
export function goodToString(el:Good){
    return el.name + " : " + el.totalnumber;
}
