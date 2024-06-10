import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./components/register/register.component";
import {LoginComponent} from "./components/login/login.component";
import {ProductspageComponent} from "./components/productspage/productspage.component";
import {ProductdetailsComponent} from "./components/productdetails/productdetails.component";
import {UserprofileComponent} from "./components/userprofile/userprofile.component";
import {CompareproductsComponent} from "./components/compareproducts/compareproducts.component";
import {CartComponent} from "./components/cartcomponent/cart.component";
import {CardComponent} from "./components/card/card.component";
import {AdmindashboardComponent} from "./components/admindashboard/admindashboard.component";
import {AuthGuard} from "./services/AuthGuard";

const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'products', component : ProductspageComponent },
  { path: 'products/:id', component : ProductdetailsComponent },
  { path: 'profile', component : UserprofileComponent },
  { path: 'compare', component: CompareproductsComponent },
  { path: 'cart', component: CartComponent},
  { path: 'checkout', component: CardComponent },
  { path: 'dashboard', component: AdmindashboardComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
