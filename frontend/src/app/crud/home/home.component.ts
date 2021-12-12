import { Component, OnInit } from '@angular/core';
import {User} from "../user";
import {CrudService} from "../crud.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  users: User[] = [];

  constructor(public crudService: CrudService) { }

  ngOnInit(): void {
    this.crudService.getAll().subscribe((data: User[])=>{
      console.log(data);
      this.users = data;
      console.log(this.users)
    })
  }

  deleteUser(user: string) {
    this.crudService.delete(user).subscribe(res => {
      this.users = this.users.filter(item => item.name !== user);
      console.log('User deleted!')
    })
  }

}
