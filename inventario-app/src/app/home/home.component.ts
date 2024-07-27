import { Component, OnInit } from '@angular/core';
import { Product } from '../models/product.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products: Product[] = [
    { id: 1, name: 'Product 1', quantity:2,status: 1 },
    { id: 2, name: 'Product 2', quantity:1,status: 2 },
    { id: 3, name: 'Product 3', quantity:3,status: 1 },
    { id: 4, name: 'Product 4', quantity:4,status: 2 },
    { id: 5, name: 'Product 5', quantity:5,status: 1 },
  ];
  hasProducts: boolean = false;

  ngOnInit() {
    this.checkProducts();
  }

  checkProducts() {
    this.hasProducts = this.products.length > 0;
  }

  getStatusSymbol(status: number): string {
    if (status === 1) {
      return '✅ Available';
    } else if (status === 2) {
      return '⚠️ Inactive';
    }
    return '';
  }
}