import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/users'; // <- API backend

  constructor(private http: HttpClient) {}

  getUser(): Observable<any>{
    return this.http.post<any>(`${this.apiUrl}/UsuarioInfos`, {}, {withCredentials: true});
  }


}
