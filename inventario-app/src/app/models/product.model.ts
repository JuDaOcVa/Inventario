export interface Product {
    id: number;
    idUser?: number;
    productName: string;
    quantity: number;
    status: number;
    token?: string;
  }