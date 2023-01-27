import React, { useRef, useState } from 'react';
import logo from './logo.svg';
import { Counter } from './features/counter/Counter';
import './App.css';
import { getModeForUsageLocation } from 'typescript';
import { AppControllerService, Good, Rack, Usercard } from './services/openapi';
import { List } from './components/UI/List/List';
import { Alert, Button, Container } from 'react-bootstrap';
import { ThemeSwitch } from './components/UI/ThemeSwitch/ThemeSwitch';
import { Panel } from './components/UI/Panel/Panel';
import { useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { GetPanel } from './components/UI/Panel/GetPanel';
import { SendPanel } from './components/UI/Panel/SendPanel';
import { CSSTransition } from 'react-transition-group';
import { createRef } from 'react';
import styles from './components/UI/Panel/Panel.module.css'
import { createStore } from '@reduxjs/toolkit';
import { Provider, TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import { useAppDispatch, useAppSelector } from './app/hooks';

function App() {
  const [theme, setTheme] = useState('dark');
  const [usercard, setUsercard] = useState<Usercard>();
  const [view,changeView] = useState('main');


  const changeUser = (usercard:Usercard)=>{
    setUsercard(usercard);
  }
  const closeBtn = () =>{
    changeView('main');
  }
  // console.log('a');
  useEffect(()=>{
    AppControllerService.profile1().then(changeUser)  
  },[])

  return (
    <div className={theme=='dark'?'DarkApp':'LightApp'}>

      <header className="App-header">
        <span >
          <h2 className={theme=='dark'?'DarkText':'LightText'}>{
          "Добро пожаловать" +((!!usercard && "firstname" in usercard)?(", " + usercard.firstname):(""))
          }</h2>
        </span>
      
        <span>
          <Button variant="w-100 btn btn-lg btn-primary m-4" onClick={()=>{
            changeView('get')
          }}>
            <h3 className={theme=='dark'?'DarkText':'LightText'}>Получить</h3>
          </Button>
          <Button variant="w-100 btn btn-lg btn-info m-4" onClick={()=>{
            changeView('send')
          }}>
            <h3 className={theme=='dark'?'DarkText':'LightText'}>Положить</h3>
          </Button>
          <ThemeSwitch setTheme={setTheme}/>
        </span>
        <Container>
          <CSSTransition
          in={view=='get'}

          timeout={1000}
          classNames="panel"
          unmountOnExit
          // onEnter={() => console.log('ent')}
          // onExited={() => changeView('')}
        >
          {/* <GetPanel theme={theme}/> */}
          {<GetPanel theme={theme} close={closeBtn}/>}
          </CSSTransition>
          <CSSTransition
          in={view=='send'}

          timeout={1000}
          classNames="panel"
          unmountOnExit
          // onEnter={() => console.log('ent')}
          // onExited={() => changeView('')}
        >
          {/* <GetPanel theme={theme}/> */}
          {<SendPanel theme={theme} close={closeBtn}/>}
          </CSSTransition>
          </Container>

        {/* <div>
          <List<Good> title="Goods" content={c.goods()} />
          <List<Usercard> title="Profiles" content={AppControllerService.profiles()} />
          <List<Rack> title="Racks" content={AppControllerService.racks()}/>
        </div>
        <img src={logo} className="App-logo" alt="logo" />
        <Counter />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <span>
          <span>Learn </span>
          <a
            className="App-link"
            href="https://reactjs.org/"
            target="_blank"
            rel="noopener noreferrer"
          >
            React
          </a>
          <span>, </span>
          <a
            className="App-link"
            href="https://redux.js.org/"
            target="_blank"
            rel="noopener noreferrer"
          >
            Redux
          </a>
          <span>, </span>
          <a
            className="App-link"
            href="https://redux-toolkit.js.org/"
            target="_blank"
            rel="noopener noreferrer"
          >
            Redux Toolkit
          </a>
          ,<span> and </span>
          <a
            className="App-link"
            href="https://react-redux.js.org/"
            target="_blank"
            rel="noopener noreferrer"
          >
            React Redux
          </a>
        </span> */}
      </header>
    </div>
  );
}

export default App;

export function isNumeric(value:string|number) {
  return /^-?\d+$/.test(value.toString());
}