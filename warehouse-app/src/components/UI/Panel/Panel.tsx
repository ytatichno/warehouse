import { relative } from 'path/posix';
import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Usercard } from '../../../services/openapi/models/Usercard';
import styles from './Panel.module.css';


export function Panel(props: {theme:string, close:()=>void,children:JSX.Element}) {

  return (
    <div className={(props.theme=='dark'?styles.darkpanel:styles.lightpanel) + ' ' + styles.rounded_corners + ' ' + styles.over}>
      <div className={styles.wrap}>
        <button type="button" className="btn-close" aria-label="Close" onClick={props.close}></button>
      </div>
    <div>
      {props.children}
    </div>
    </div>
  );
}