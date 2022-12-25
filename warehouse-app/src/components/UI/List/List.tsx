import React, { ReactNode, useState } from 'react';
import styles from './List.module.css';
import { CancelablePromise } from '../../../services/openapi';
import { Good, goodToString } from '../../../services/openapi/models/Good';
import { IListProps } from './IListProps';
import { Usercard, usercardToString } from '../../../services/openapi/models/Usercard';
import { Rack, rackToString } from '../../../services/openapi/models/Rack';



export function List<T extends Good|Usercard|Rack>(props: {title:string,content:T[],onClick:(event:any,items:T[])=>void}) {

  


  function generateOne(element:T,index:number,onClick:(event:Event,items:T[])=>void) {

    return <div className={styles.wrapper}>
      <div className={styles.item}>{stringFromT(element)}</div>
      <div>
        <button value={index} className={styles.button} onClick={(event:any)=>{
        onClick(event,props.content);
      }}>+</button>
      </div>
    </div>
  }
  return (
    <div>
      <h3>{props.title}</h3>
      <div className={styles.List}>
        {props.content.length>0?props.content.map((element:T,index:number)=>
        {return generateOne(element,index,props.onClick)}):"Загрузка..."}
      </div>
    </div>
  );
}
export function stringFromT<T extends Good|Usercard|Rack>(obj:T){
  let func = (obj:T) => 'unrecognized'
    if("totalnumber" in obj)
      func = goodToString;
    else if("lastname" in obj)
      func = usercardToString;
    else if("addr" in obj)
      func = rackToString;
    return func(obj);
}

// function elementToString(element:Good){
//   return element.id + " : " + element.name + " : " + element.descr;
// }
// function elementToString(element:Usercard){
//   return element.id + " : " + element.lastname + " : " + element.firstname;
// }
// function elementToString(element:Rack){
//   const goodField =(!!element.good && "name" in element.good)?element.good.name:'null';
//     return element.id + " : " + element.addr + " : " + goodField + " : " + element.number;
// }