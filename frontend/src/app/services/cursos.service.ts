import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CursosService {

  private apiUrl = 'http://localhost:8080/content'; // <- API backend

  constructor(private http: HttpClient) {}

  getCursos(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/lerCursos`,{withCredentials:true});
  }

  cadastrarCurso(data: any): Observable<any>{
    console.log(data)
    return this.http.post(`${this.apiUrl}/cadastrarCurso`, data, {withCredentials:true,
    responseType: 'text' as 'json'
    });
  }

}
