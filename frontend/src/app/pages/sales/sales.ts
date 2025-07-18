import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-sales',
  imports: [CommonModule],
  templateUrl: './sales.html',
  styleUrl: './sales.css'
})
export class Sales {

  private baseUrl = environment.apiUrl;

  sales: any[] = [];

  constructor(private http: HttpClient) {}

  

  ngOnInit() {
    const token = localStorage.getItem('jwt');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.get<any[]>(`${this.baseUrl}/api/sales`, {
      headers: headers,
      withCredentials: true
    }).subscribe({
      next: data => {
        console.log(data)
        this.sales = data
      },
      error: err => {
        console.error('Failed to fetch sales data', err);
        alert('Could not load sales entries.');
      }
    });
  }
}
