import { Product } from "./product.model";

export interface GetProductResponse {
    message: string;
    data:Product[];
    status: number;
  }