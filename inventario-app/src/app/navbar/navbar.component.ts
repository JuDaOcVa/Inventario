import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  userName: string = localStorage.getItem('userName') || '';
  currentDateTime?: string;

  constructor(private router: Router) {
    this.updateDateTime();
  }

  ngOnInit(): void {
    setInterval(() => {
      this.updateDateTime();
    }, 1000);
  }

  updateDateTime(): void {
    const now = new Date();
    this.currentDateTime = now.toLocaleString();
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}