import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private apiUrl = 'http://localhost:8081/payment/pay'; // URL do seu backend

  constructor(private http: HttpClient) {}

  processPayment(paymentData: any): Observable<any> {
    const user = this.getUserFromStorage();
    const token = user?.token ?? '';

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.post(this.apiUrl, paymentData, { headers });
  }

  getUserFromStorage() {
    const userString = localStorage.getItem('user');
    if (userString) {
      return JSON.parse(userString);
    }
    return null;
  }
}
