import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/users'; // <- API backend

  private currentUserSubject = new BehaviorSubject<any>(this.getUserFromStorage());

  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  // GET - lista todos os usuários
  getUsers(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}`);
  }

  getUser(): Observable<any>{
    return this.http.post<any>(`${this.apiUrl}/UsuarioInfos`, {}, {withCredentials: true});
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

  getUserFromStorage() {
  const raw = localStorage.getItem('user');
  if (!raw) return null;

  try {
    return JSON.parse(raw);
  } catch (e) {
    console.warn('Erro ao ler user do localStorage:', e);
    localStorage.removeItem('user'); // limpa o dado inválido
    return null;
  }
}

  loginLocal(user: any) {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  logout(){
    return this.http.post(`${this.apiUrl}/logout`, {}, { withCredentials: true, responseType: 'text' })
    .subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => console.error('Erro ao deslogar', err)
    });
  }


}
