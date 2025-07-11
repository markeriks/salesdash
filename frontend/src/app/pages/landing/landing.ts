import { Component, signal } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-landing',
  imports: [FormsModule, RouterModule],
  templateUrl: './landing.html',
  styleUrl: './landing.css'
})
export class Landing {
  name = "SalesDash";
  title = "Data done right.";

  email = ''

  constructor(private router: Router) {}

  goToLogin() {
    this.router.navigate(['/login'], { queryParams: {email: this.email} })
  }
}
