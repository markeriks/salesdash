import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {

  private baseUrl = environment.apiUrl;

  selectedFile: File | null = null;
  metrics: any = null;

  constructor(private http: HttpClient, private router: Router) {}

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.[0] ?? null;
  }

  uploadFile() {
    if (!this.selectedFile) {
      alert('Please select a CSV file first.');
      return;
    }

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    const token = localStorage.getItem('jwt');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    this.http.post(`${this.baseUrl}/api/upload`, formData, {
      headers: headers,
      withCredentials: true
    }).subscribe({
      next: () => {
        alert('File uploaded!');
        this.fetchMetrics();
      },
      error: err => {
        alert('Upload failed.');
        console.error(err);
      }
    });
  }

  fetchMetrics() {
    const token = localStorage.getItem('jwt');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    this.http.get(`${this.baseUrl}/api/dashboard/metrics`, {
      headers: headers,
      withCredentials: true
    }).subscribe({
      next: data => this.metrics = data,
      error: err => console.error('Failed to load metrics', err)
    });
  }

  viewSalesData() {
    this.router.navigate(['/sales']);
  }

  ngOnInit() {
    this.fetchMetrics();
  }
}

