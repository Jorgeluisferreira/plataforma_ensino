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

  getCursosUsuario(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/lerCursosUsuario`,{withCredentials:true});
  }

  getCursoById(idCurso: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/lerCurso/${idCurso}`,{withCredentials:true})
  }

  getAulasCurso(idCurso: any): Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/lerAulaPorCurso/${idCurso}`,{withCredentials:true});
  }

  getAulas(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/lerAulas`,{withCredentials:true});
  }

  cadastrarCurso(data: any): Observable<any>{
    console.log(data)
    return this.http.post(`${this.apiUrl}/cadastrarCurso`, data, {withCredentials:true});
  }

  cadastrarAula(data:any, idCurso: any): Observable<any>{
    return this.http.post(`${this.apiUrl}/${idCurso}/adicionarAula`, data, {withCredentials:true});
  }

  assinarCurso(idCurso:any): Observable<any>{
     return this.http.post(`${this.apiUrl}/assinarCurso/${idCurso}`, {}, {withCredentials:true});
  }

  gerarCertificado(idCurso: any,tipo:any): Observable<any> {
    return this.http.get(`${this.apiUrl}/gerarEstadoCurso/${idCurso}/${tipo}`, { responseType: 'blob', withCredentials: true });
  }

  concluirCurso(idCurso: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/concluirEtapa/${idCurso}`, {}, {withCredentials:true});
  }

  gerarRelatorio(formato: any): Observable<any> {
    return this.http.get(`${this.apiUrl}/gerarListaMatriculas/${formato}`, { responseType: 'blob', withCredentials: true });
  }
}
