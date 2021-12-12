import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User} from "./user";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CrudService {

  private apiServer = "http://localhost:8080";

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  }

  constructor(private httpClient: HttpClient) { }

  create(user:User){
    return this.httpClient.post<User>(this.apiServer+'/users', user)
  }

  getByName(name: string){
    return this.httpClient.get<User>(this.apiServer+'/'+name);
  }

  getAll(): Observable<any> {
    console.log("test")
    return this.httpClient.get<User[]>(this.apiServer+'/users')
  }

  update(name: string, user:User){
    return this.httpClient.put<User>(this.apiServer + '/users/' + name, user)
  }

  delete(name: string){
    return this.httpClient.delete<User>(this.apiServer + '/users/' + name)
  }

  find(name: string) {
    return this.httpClient.get(this.apiServer+'/users/'+name)
  }
}
