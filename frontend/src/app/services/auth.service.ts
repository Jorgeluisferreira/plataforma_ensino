import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8082/users'; // <- API backend

  private usuarioSubject = new BehaviorSubject<any>(null);

  constructor(private http: HttpClient) { 
    const usuarioSalvo = localStorage.getItem('usuario');
    if (usuarioSalvo) {
      this.usuarioSubject.next(JSON.parse(usuarioSalvo));
    }
  }

  // GET - lista todos os usuários
  getUsers(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  getUser(): Observable<any>{
    return this.http.post<any>(`${this.apiUrl}/UsuarioInfos`, {}, {withCredentials: true});
  }

  getUserObs(): Observable<any> {
    return this.usuarioSubject.asObservable();
  }
  
  // POST - cria um novo usuário
  createUser(data: any): Observable<any> {
    console.log(data)
    return this.http.post(`${this.apiUrl}/cadastrarAluno`, data, {
    responseType: 'text' as 'json'
    });
  }

  createProf(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/cadastrarProfessor`, data, {
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



  logout(){
    return this.http.post(`${this.apiUrl}/logout`, {}, { withCredentials: true, responseType: 'text' })
    .subscribe({
      next: (res) => {
        localStorage.removeItem('usuario');
      },
      error: (err) => console.error('Erro ao deslogar', err)
    });
  }


}
