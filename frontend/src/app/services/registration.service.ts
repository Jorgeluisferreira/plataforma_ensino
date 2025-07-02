import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private apiUrl = 'http://localhost:8080/users'; // <- API backend

  constructor(private http: HttpClient) {}

  // GET - lista todos os usuários
  getUsers(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  // POST - cria um novo usuário
  createUser(data: any): Observable<any> {
    console.log(data)
    return this.http.post(`${this.apiUrl}/cadastrarAluno`, data, {
    responseType: 'text' as 'json'
    });
  }

  //POST - login de usuario
  login(email:string, password:string): Observable<any>{
    const headers = {
      Authorization: 'Basic ' + btoa(`${email}:${password}`)
    };
    return this.http.post(`${this.apiUrl}/login`,{}, {headers, withCredentials: true, responseType: 'text'});
  }

  // PUT - atualiza usuário
  updateUser(id: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, data);
  }

  // DELETE - remove usuário
  deleteUser(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
