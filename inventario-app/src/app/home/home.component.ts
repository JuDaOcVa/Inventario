import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from '../models/product.model';
import { GetProductResponse } from '../models/getproduct-response.model';
import { GenericResponse } from '../models/generic-response.model';
import { API_URLS } from 'src/config/api-urls';
import { CONSTANTS } from 'src/config/constants';
import Swal from 'sweetalert2';

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
    if (this.products && Array.isArray(this.products)) {
    this.hasProducts = this.products.length > 0;
    }
  }

  getStatusSymbol(status: number): string {
    if (status === CONSTANTS.STATUS_ACTIVE) {
      return '✅ Available';
    } else if (status === CONSTANTS.STATUS_INACTIVE) {
      return '⚠️ Inactive';
    }
    return '';
  }

  saveProduct(product: any = {}) {
    product.id = product.id || 0;
    product.productName = product.productName || '';
    product.quantity = product.quantity || 0;
    product.status = product.status || CONSTANTS.STATUS_ACTIVE;
    product.token = localStorage.getItem('userToken') || '';
    
    Swal.fire({
      html: `
        <label for="name">Product:</label>
    <input id="name" class="swal2-input" value="${product.productName}">
    
    <label for="quantity">Quantity:</label>
    <input id="quantity" class="swal2-input" type="number" value="${product.quantity}">
    
    <label for="status">Status:</label>
    <select id="status" class="swal2-select">
      <option value="${CONSTANTS.STATUS_ACTIVE}" ${product.status === CONSTANTS.STATUS_ACTIVE ? 'selected' : ''}>
        ${this.getStatusSymbol(CONSTANTS.STATUS_ACTIVE)}
      </option>
      <option value="${CONSTANTS.STATUS_INACTIVE}" ${product.status === CONSTANTS.STATUS_INACTIVE ? 'selected' : ''}>
        ${this.getStatusSymbol(CONSTANTS.STATUS_INACTIVE)}
      </option>
    </select>
      `,
      showCancelButton: true,
      confirmButtonText: 'Save',
      cancelButtonText: 'Cancel',
      confirmButtonColor: '#3325FC',
      cancelButtonColor: '#FF0000',
    }).then((result) => {
      if (result.isConfirmed) {
        const tempProduct: Product = {
          ...product,
          productName: (document.getElementById('name') as HTMLInputElement).value,
          quantity: +(document.getElementById('quantity') as HTMLInputElement).value,
          status: +(document.getElementById('status') as HTMLSelectElement).value,
          token: localStorage.getItem('userToken') || '',
        };
        this.http.post<GenericResponse>(API_URLS.SAVEPRODUCT, tempProduct).subscribe(response => {
          if (response.status === 200) {
            Swal.fire({
              icon: 'success',
              title: response.message,
            }).then(() => {
              this.ngOnInit();
              });
          } else{
            Swal.fire({
              icon: 'error',
              title: 'Saving product failed',
              text: response.message,
            });
          }
        },
        error => {
          Swal.fire({
            icon: 'error',
            title: 'Saving product failed',
            text: 'Error : ' + error.message,
          });
        });
      }
    });
  }
  deleteProduct(product: Product) {
    Swal.fire({
      title: 'Are you sure to delete this product?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#FF0000',
      cancelButtonColor: '#3325FC',
      confirmButtonText: 'Delete',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        product.status = CONSTANTS.STATUS_DELETED;
        this.http.post<GenericResponse>(API_URLS.SAVEPRODUCT, product).subscribe(response => {
            if (response.status == 200) {
              Swal.fire(
                'Deleted!',
                response.message,
                'success'
              );
              this.ngOnInit();
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Deleting product failed',
                text: response.message,
              });
            }
          },
          error => {
            Swal.fire({
              icon: 'error',
              title: 'Deleting product failed',
              text: 'Error : ' + error.message,
            });
          }
        );
      }
    });
  }
}