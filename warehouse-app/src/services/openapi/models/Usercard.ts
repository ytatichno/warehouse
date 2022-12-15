/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type Usercard = {
    id?: number;
    lastname?: string;
    firstname?: string;
    birthday?: string;
    roles?: string;
};
export function usercardToString(el:Usercard){
    return el.id + " : " + el.lastname + " : " + el.firstname;
}
