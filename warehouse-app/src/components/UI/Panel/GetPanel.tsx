import { relative } from 'path/posix';
import React, { useEffect } from 'react';
import { useState } from 'react';
import { Alert, Button, Container } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import { CSSTransition } from 'react-transition-group';
import { isNumeric } from '../../../App';
import { AppControllerService, Good } from '../../../services/openapi';
import { List } from '../List/List';
import { Panel } from './Panel';
import styles from './Panel.module.css';



export function GetPanel(props: {theme:string, close:()=>void}) {
  const [status,setStatus] = useState('primary');
  const [number,setNumber] = useState(0);
  const empty:Good = {name:"Выберите товар"};
  const successGood:Good = {name:"Готово"};
  const [chosen,setChosen] = useState(empty);
  let listG:Good[] = [];
  const [goods,setGoods] = useState(listG);
  const goodsProm = AppControllerService.goods();
  const getGood = (event:React.ChangeEvent<HTMLInputElement>, items:Good[]) => {
    setStatus('primary');
    setChosen(items[Number(event.target.value)]);
    // const chosen:Good = items[Number(event.target.value)];
    console.log("🚀 ~ file: GetPanel.tsx:19 ~ chosen", chosen);
    console.log(chosen.name);

    
  }
  useEffect(()=>{goodsProm.then(setGoods)},[goods])
  return (
    <Panel theme={props.theme} close={props.close}>
      <div>
          <h2 style={{marginBottom:'1%'}}>Товары</h2>
          <Alert
            variant={status}
           
            >
              <div className={styles.top_wrap}>
              <div className={styles.big_naming}>{chosen.name}</div>
              <input className={styles.input} placeholder={'Сколько?'} onChange={(e)=>{setNumber(Number(e.target.value))}}></input>
              <Button className={styles.btn} variant='warning' onClick={()=>{
                if(number<=0||!isNumeric(number)||chosen==null||chosen.id==null){
                  console.log('wrong params');
                  setStatus('warning');
                }else{
                  AppControllerService.getGood(chosen.id, number).then(()=> 
                  {setStatus('success');
                 setChosen(successGood)}
                  ).catch(()=>setStatus('danger'));
                }
              }}>Получить</Button>
            </div>
            </Alert>
              <List<Good> title="" content={goods.sort((a,b)=>a.name&&b.name?a.name.localeCompare(b.name):0)} onClick={getGood} />
      </div>
    </Panel>
  );
}
