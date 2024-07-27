import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product.model';
import { GetProductResponse } from '../models/getproduct-response.model';
import { API_URLS } from 'src/config/api-urls';
import { CONSTANTS } from 'src/config/constants';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  hasProducts: boolean = false;
  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchProducts();
  }

  fetchProducts() {
    const token = localStorage.getItem('userToken'); 
    const headers = new HttpHeaders().set('token', token || '');

    this.http.get<GetProductResponse>(API_URLS.GETPRODUCTS, { headers })
      .subscribe((response) => {
        this.products = response.data;
        this.checkProducts();
      });
  }

  checkProducts() {
    this.hasProducts = this.products.length > 0;
  }

  getStatusSymbol(status: number): string {
    if (status === CONSTANTS.STATUS_ACTIVE) {
      return '✅ Available';
    } else if (status === CONSTANTS.STATUS_INACTIVE) {
      return '⚠️ Inactive';
    }
    return '';
  }
}