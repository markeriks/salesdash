import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [RouterModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  email = '';

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.email = params['email']
    })
  }

  login(loginForm: NgForm) {
    
    const {email, password} = loginForm.value;

    console.log("Sending login", email, password)
    const url = 'http://localhost:8080/api/auth/login';

    this.http.post<{ token: string }>(url,
      {
        email,
        password
      }, {
        withCredentials: true
      }
    ).subscribe({
      next: (response) => {
        localStorage.setItem('jwt', response.token)
        alert('Sign in successful!');
        this.router.navigate(['/dashboard']);
      },
      error: (e) => {
        alert('Login failed' + e.error.message);
      }
    })
  }
}
