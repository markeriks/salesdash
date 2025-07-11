import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-signup',
  imports: [RouterModule, FormsModule],
  templateUrl: './signup.html',
  styleUrl: './signup.css'
})
export class Signup {

  constructor(private http: HttpClient, private router: Router) {}

  signup(signupForm: NgForm) {
    const {username, email, password} = signupForm.value;
    const url = 'http://localhost:8080/api/auth/signup';

    this.http.post(url, {
      username,
      email,
      password
    }, {
      withCredentials: true
    }).subscribe({
      next: () => {
        alert('Account created!');
        this.router.navigate(['/login']);
      },
      error: (e) => {
        alert('Signup failed: ' + e.error.message);
      }
    })
  }
}
