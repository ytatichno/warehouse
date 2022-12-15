/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Good } from '../models/Good';
import type { LogEntry } from '../models/LogEntry';
import type { Outcoming } from '../models/Outcoming';
import type { Rack } from '../models/Rack';
import type { Usercard } from '../models/Usercard';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AppControllerService {

    /**
     * Getting authenticated profile usercard
     * @returns Usercard OK
     * @throws ApiError
     */
    public static profile1(): CancelablePromise<Usercard> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/profile',
        });
    }

    /**
     * Updating authenticated profile userecard
     * @param requestBody 
     * @returns Usercard OK
     * @throws ApiError
     */
    public static profile(
requestBody: Usercard,
): CancelablePromise<Usercard> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/app/profile',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Get usercard of user with id(in credentials table)
     * @param id 
     * @returns Usercard OK
     * @throws ApiError
     */
    public static profile2(
id: number,
): CancelablePromise<Usercard> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/profile/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * Change usercard of user with id(in credentials table)
     * @param id 
     * @param requestBody 
     * @returns Usercard OK
     * @throws ApiError
     */
    public static fillUsercard(
id: number,
requestBody: Usercard,
): CancelablePromise<Usercard> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/app/profile/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Get number of good from rack with address
     * @param addr 
     * @param number 
     * @returns Good OK
     * @throws ApiError
     */
    public static getGoodByAddr(
addr: string,
number: number,
): CancelablePromise<Good> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/good',
            query: {
                'addr': addr,
                'number': number,
            },
        });
    }

    /**
     * Put number of good to rack with address
     * @param addr 
     * @param number 
     * @returns any OK
     * @throws ApiError
     */
    public static putGoodByAddr(
addr: string,
number: number,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/app/good',
            query: {
                'addr': addr,
                'number': number,
            },
        });
    }

    /**
     * Getting general info about good with id
     * @param id 
     * @returns Good OK
     * @throws ApiError
     */
    public static good(
id: number,
): CancelablePromise<Good> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/good-info/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * DEPRECATED!Manual changing good in goods table
     * @param id 
     * @param requestBody 
     * @returns Good OK
     * @throws ApiError
     */
    public static changeGood(
id: number,
requestBody: Good,
): CancelablePromise<Good> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/app/good-info/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * DEPRECATED!Manual adding good in goods table
     * @param id 
     * @param requestBody 
     * @returns Good OK
     * @throws ApiError
     */
    public static newGood(
id: number,
requestBody: Good,
): CancelablePromise<Good> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/app/good-info/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * DEPRECATED!Manual deleting good in goods table
     * @param id 
     * @returns any OK
     * @throws ApiError
     */
    public static delGood(
id: number,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/app/good-info/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * Bind rack on good with id
     * @param id 
     * @param addr 
     * @returns any OK
     * @throws ApiError
     */
    public static bindRack(
id: number,
addr: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/app/bind-rack',
            query: {
                'id': id,
                'addr': addr,
            },
        });
    }

    /**
     * set up new rack with address
     * @param addr 
     * @returns any OK
     * @throws ApiError
     */
    public static createRack(
addr: string,
): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/app/rack',
            query: {
                'addr': addr,
            },
        });
    }

    /**
     * delete rack by address
     * @param addr 
     * @returns Rack OK
     * @throws ApiError
     */
    public static delRack(
addr: string,
): CancelablePromise<Rack> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/app/rack',
            query: {
                'addr': addr,
            },
        });
    }

    /**
     * get info about all racks
     * @returns Rack OK
     * @throws ApiError
     */
    public static racks(): CancelablePromise<Array<Rack>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/racks',
        });
    }

    /**
     * get info about all racks binded on good with id
     * @param id 
     * @returns Rack OK
     * @throws ApiError
     */
    public static goodRacks(
id: number,
): CancelablePromise<Array<Rack>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/racks/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * get all queue logs
     * @returns Outcoming OK
     * @throws ApiError
     */
    public static queue(): CancelablePromise<Array<Outcoming>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/queue',
        });
    }

    /**
     * Getting all profiles usercards(info), request sender has to have all roles
     * @returns Usercard OK
     * @throws ApiError
     */
    public static profiles(): CancelablePromise<Array<Usercard>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/profiles',
        });
    }

    /**
     * get all logs
     * @returns LogEntry OK
     * @throws ApiError
     */
    public static logs(): CancelablePromise<Array<LogEntry>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/logs',
        });
    }

    /**
     * Getting all goods information
     * @returns Good OK
     * @throws ApiError
     */
    public static goods(): CancelablePromise<Array<Good>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/goods',
        });
    }

    /**
     * getting good from racks
     * searching all rack associated with this good and sequentially get from each
 * til they run out or the requested amount is reached
     * @param id 
     * @param number 
     * @returns Good OK
     * @throws ApiError
     */
    public static getGood(
id: number,
number: number,
): CancelablePromise<Good> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/app/good/{id}',
            path: {
                'id': id,
            },
            query: {
                'number': number,
            },
        });
    }

}
