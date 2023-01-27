import { isNumberObject } from 'node:util/types';
import React, { useState } from 'react';
import { useEffect } from 'react';
import { Alert, Button, Container } from 'react-bootstrap';
import { CSSTransition } from 'react-transition-group';
import { isNumeric } from '../../../App';
import { selectCount } from '../../../features/counter/counterSlice';
import { Rack } from '../../../services/openapi';
import { Good } from '../../../services/openapi/models/Good';
import { AppControllerService } from '../../../services/openapi/services/AppControllerService';
import { List } from '../List/List';
import { Select } from '../Select/Select';
import { Panel } from './Panel';
import styles from './Panel.module.css';



export function SendPanel(props: {theme:string, close:()=>void}) {
  const [status,setStatus] = useState('primary');
  const [number,setNumber] = useState(0);
  const [newNumber,setNewNumber] = useState(0);
  const [name,setName] = useState('');
  const [description,setDescription] = useState('');
  const emptyRack:Rack = {addr:"..."}
  const [rack,setRack] = useState(emptyRack);
  const emptyGood:Good = {name:"Выберите товар"};
  const successGood:Good = {name:"Готово"};
  const [chosen,setChosen] = useState(emptyGood)
  const goodRacksProm = chosen.id?AppControllerService.goodRacks(chosen.id):AppControllerService.goodRacks(0);
  const listR:Rack[]=[];
  const [goodRacks,setGoodRacks] = useState(listR);
  const emptyRacksProm = AppControllerService.goodRacks(-1);
  const [emptyRacks,setEmptyRacks] = useState(listR);
  let listG:Good[] = [];
  const [goods,setGoods] = useState(listG);
  const goodsProm = AppControllerService.goods()
  const [showNGP,setShowNGP] = useState(false); //NGP = newGoodPanel
  const [showSpecifier,setShowSpecifier] = useState(true); //Specifier - blue Alert where you can specify quantity
  const getGood = (event:React.ChangeEvent<HTMLInputElement>, items:Good[]) => {
    setStatus('primary');
    setRack(emptyRack);
    setChosen(items[Number(event.target.value)]);
    // const chosen:Good = items[Number(event.target.value)];
    // console.log(chosen.name + ' ' + chosen.id);

    
  }
  // goodRacksProm.then(setGoodRacks);
  useEffect(()=>{goodRacksProm.then(setGoodRacks)},[chosen]);
  useEffect(()=>{goodsProm.then(setGoods)},[goods]);
  return (
    <Panel theme={props.theme} close={props.close}>
      <div>
        <div className={styles.top_wrap}>
          <h2 style={{marginBottom:'1%',marginRight:'1rem'}}>Товары</h2>
          <Button variant='outline-warning' onClick={()=>{emptyRacksProm.then(setEmptyRacks);setShowNGP(true);setShowSpecifier(false)}}>Новый товар</Button>
        </div>

        <CSSTransition
        in={showNGP}
        timeout={300}
        classNames="alert"
        unmountOnExit
        // onEnter={() => setShowButton(false)}
         onExited={() => setShowSpecifier(true)}
      >
        <Alert
            variant={'info'}
            dismissible
            onClose={()=>setShowNGP(false)}
            >
              <div className={styles.top_wrap}>
                <input className={styles.naming} placeholder={'Наименование?'} onChange={(e)=>{setName(e.target.value)}}></input>
                <input className={styles.input} placeholder={'Сколько?'} onChange={(e)=>{setNewNumber(Number(e.target.value))}}></input>
                <div className={styles.select}>
                  <Select options={emptyRacks} 
                  defaultValue="Стеллаж" value={rack.id?rack.id.toString():'0'} onChange={(id:any)=>{  
                    setStatus('primary')
                    console.log(id===4);             
                    const changedTo:any = emptyRacks.find(x => x.id == id);
                    if(changedTo)
                      setRack(changedTo);
                    }}  />
                </div>
                <input className={styles.description} placeholder={'Описание?'} onChange={(e)=>{setDescription(e.target.value)}}></input>
                <Button className={styles.btn} variant='warning' onClick={()=>{
                  if(newNumber<=0||!isNumeric(newNumber)||rack.id==null||rack.addr==null||rack.addr==''){
                    console.log('wrong params at new');
                    // setStatus('warning')
                  }else{
                    let newGood:Good = {id:0,name:name,descr:description,totalnumber:0};
                    AppControllerService.newGood(newGood).then((addedGood:Good)=>{
                      console.log('new good')
                      newGood = addedGood;
                      if(newGood.id&&rack.addr)
                        AppControllerService.bindRack(newGood.id,rack.addr).then(()=>{
                          console.log('binded')
                          if(rack.addr)
                            AppControllerService.putGoodByAddr(rack.addr,newNumber).then(()=>console.log('done'));
                            console.log('and putted')
                        })
                    });
                    
                    

                  //   AppControllerService.putGoodByAddr(rack.addr,number).then(()=> 
                  //   {setStatus('success');
                  //  setChosen(successGood)
                  //  setRack(emptyRack);
                  // }
                    // ).catch(()=>setStatus('danger'));
                  }
                }}>Добавить</Button>
                
              
            </div>
        </Alert>
      </CSSTransition>

        {showSpecifier?<Alert
            variant={status}
            >
              <div className={styles.top_wrap}>
                <div className={styles.naming}>{chosen.name}</div>
                <input className={styles.input} placeholder={'Сколько?'} onChange={(e)=>{setNumber(Number(e.target.value))}}></input>
                <div className={styles.select}>
                  <Select options={goodRacks} 
                  defaultValue="Стеллаж" value={rack.id?rack.id.toString():'0'} onChange={(id:any)=>{  
                    setStatus('primary')
                    console.log(id===4);             
                    const changedTo:any = goodRacks.find(x => x.id == id);
                    if(changedTo)
                      setRack(changedTo);
                    }}  />
                </div>
                <Button className={styles.btn} variant='warning' onClick={()=>{
                  if(number<=0||!isNumeric(number)||chosen==null||chosen.id==null||rack.id==null||rack.addr==null||rack.addr==''){
                    console.log('wrong params');
                    setStatus('warning')
                  }else{
                    AppControllerService.putGoodByAddr(rack.addr,number).then(()=> 
                    {setStatus('success');
                   setChosen(successGood)
                   setRack(emptyRack);
                  }
                    ).catch(()=>setStatus('danger'));
                  }
                }}>Положить</Button>
              
            </div>
        </Alert>:''}
        
            <List<Good> title="" content={goods.sort((a,b)=>a.name&&b.name?a.name.localeCompare(b.name):0)} onClick={getGood} />

      </div>
    </Panel>
  );
}