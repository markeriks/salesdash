import { Routes } from '@angular/router';
import { Landing } from './pages/landing/landing';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { Login } from './pages/login/login';
import { Signup } from './pages/signup/signup';
import { Dashboard } from './pages/dashboard/dashboard';
import { Sales } from './pages/sales/sales';
import { authGuard } from './auth-guard';

export const routes: Routes = [
    { path: '', component: Landing },
    { path: '', component: AuthLayout,
        children: [
            { path: 'login', component: Login },
            { path: 'signup', component: Signup }
        ]
     },
     {
        path: 'dashboard', component: Dashboard, canActivate: [authGuard]
     },
     {
        path: 'sales', component: Sales, canActivate: [authGuard]
     },
    { path: '**', redirectTo: '' }
];
