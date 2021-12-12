import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {Router} from "@angular/router";
import {CrudService} from "../crud.service";

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css']
})
export class CreateComponent implements OnInit {

  userForm!: FormGroup;

  constructor(
    public fb: FormBuilder,
    private router: Router,
    public crudService: CrudService
  ) { }

  ngOnInit(): void {
    this.userForm = this.fb.group({
      name:[''],
      pw:[''],
      privileged:['']
    })
  }

  submitForm() {
    this.crudService.create(this.userForm.value).subscribe(res => {
      console.log('Product created');
      this.router.navigateByUrl('/crud/home');
    })
  }
}
