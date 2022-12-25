import React, { ReactNode, useState } from 'react';
import { CancelablePromise, Rack } from '../../../services/openapi';
import { stringFromT } from '../List/List';
interface Props {
  children?: ReactNode
  // any props that come into the component
}
export function Select<T extends Rack>(props:{options:T[],defaultValue:string, value:string, onChange:(event:any)=>void}){
  return (
    <select 
        value={props.value}
        onChange={event => props.onChange(event.target.value)}
        // placeholder={props.defaultValue}
        style={{width:"100%",height:"100%"}}
        >
          <option disabled hidden value = "0">{props.defaultValue}</option>
          {props.options.length>0?props.options.map((option:T,index:number) =>
            <option key={index} value = {option.id}> {stringFromT(option)} </option>            
            ):"..."}
        </select>

  );
};
