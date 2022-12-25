import React from 'react';
import { useState } from 'react';
import classes from './ThemeSwitch.module.css';


export function ThemeSwitch(props: {setTheme:(theme:string)=>void}) {
  const [isDark,setDark]=useState(true);
  props.setTheme(isDark?'dark':'light')
  return(
    <div className={classes.container}>
      <div className={classes.ico}>
        <svg width="51" height="51" xmlns="http://www.w3.org/2000/svg" >
        <g>
        <ellipse ry="24" rx="24" id="svg_3" cy="26" cx="26" stroke="#000" fill="#000000"/>
        <ellipse ry="22" rx="22" id="svg_4" cy="26" cx="26" stroke="#000" fill="#ffc107"/>
        <path stroke="#000" id="svg_6" d="m400.21382,330.21054l0.45558,0c-19.97315,-0.22779 -30.80546,-13.38522 -30.80546,-29.90197c0,-16.51464 13.79359,-29.90197 30.80546,-29.90197l-1.36674,-0.68337c-0.35567,10.47552 0.11636,26.33799 0.57194,36.96347s-399.8746,-306.6867 0.33922,23.52384z" fill="#000000"/>
        <path d="M26,2 
        A20,20 270 1,0 26,50" 
        fill="#000000" />
        </g>
        </svg>
      </div>
      <label className={classes.switch}>
        <input  type="checkbox" onChange={(e:React.FormEvent<HTMLInputElement>)=>setDark(!isDark)}/>
        <span className={classes.slider}></span>
      </label>
    </div>
  );
}