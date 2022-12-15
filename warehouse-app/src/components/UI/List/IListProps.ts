
export interface IListProps<T>{
  itemsList: T[];
  getItem: (v: T, index: number) => string;
}