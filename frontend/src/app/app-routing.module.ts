import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DetailsComponent} from "./crud/details/details.component";
import {HomeComponent} from "./crud/home/home.component";
import {CreateComponent} from "./crud/create/create.component";
import {UpdateComponent} from "./crud/update/update.component";

const routes: Routes = [
  { path: 'crud', redirectTo: 'crud/home', pathMatch: 'full'},
  { path: 'crud/home', component: HomeComponent },
  { path: 'crud/details/:productId', component: DetailsComponent },
  { path: 'crud/create', component: CreateComponent },
  { path: 'crud/update/:userName', component: UpdateComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
