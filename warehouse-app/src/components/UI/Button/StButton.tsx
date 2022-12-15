import React, { ReactNode } from 'react';
import classes from './StButton.module.css';

interface Props {
  children?: ReactNode
  // any props that come into the component
}

const StButton = ({children, ...props}: Props) => {
  return (
    <button  className={classes.StBtn} {...props}  >
      {children}
    </button>
  );
};

export default StButton;