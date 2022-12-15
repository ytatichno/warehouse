import React, { ReactNode, useState } from 'react';
import styles from './List.module.css';
import { CancelablePromise } from '../../../services/openapi';
import { Good, goodToString } from '../../../services/openapi/models/Good';
import { IListProps } from './IListProps';
import { Usercard, usercardToString } from '../../../services/openapi/models/Usercard';
import { Rack, rackToString } from '../../../services/openapi/models/Rack';



export function List<T extends Good|Usercard|Rack>(props: {title:string,content:CancelablePromise<T[]>}) {
  let list:T[] = [];
  const [items,setItems] = useState(list);
  props.content.then(setItems);
  return (
    <div>
      <h3>{props.title}</h3>
      <div className={styles.List}>
        {items.map(generateOne)}
      </div>
    </div>
  );
}
function generateOne<T extends Good|Usercard|Rack>(element:T,index:number) {
  let func = (element:T) => 'unrecognized'
  if("totalnumber" in element)
    func = goodToString;
  else if("lastname" in element)
    func = usercardToString;
  else if("addr" in element)
    func = rackToString;
  return <div>{func(element)}</div>
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