import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrl = 'http://localhost:8081/payment/pay';

  constructor(private http: HttpClient) {}

  processPayment(paymentData: any): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer SEU_JWT_AQUI', // depois você substitui pelo token real
      'Content-Type': 'application/json'
    });

    return this.http.post(this.apiUrl, paymentData, { headers });
  }
}
