import { Component, OnInit } from '@angular/core';
import {User} from "../user";
import {FormControl, FormGroup, Validator, Validators, FormBuilder} from "@angular/forms";
import {CrudService} from "../crud.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css']
})
export class UpdateComponent implements OnInit {

  name!: string;
  user!: User;
  form!: FormGroup;

  constructor(
    public crudService: CrudService,
    private route: ActivatedRoute,
    private router: Router,
    public fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.name = this.route.snapshot.params['userName'];
    this.crudService.find(this.name).subscribe((data: any) => {
      this.user = data;
    })

    this.form = this.fb.group({
      name:[''],
      pw:[''],
      privileged:['']
    })
  }

  get f() {
    return this.form.controls;
  }

  submit() {
    console.log(this.form.value);
    this.crudService.update(this.name, this.form.value).subscribe(res => {
      console.log('User updated!');
      this.router.navigateByUrl('crud/home')
    })
  }

}
