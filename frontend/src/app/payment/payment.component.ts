import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonComponent } from '../components/button/button.component';
import { PaymentService } from '../services/payment.service';
import { FormsModule } from '@angular/forms'; // ✅ IMPORTAR FormsModule

@Component({
  standalone: true, // necessário para standalone component
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
  imports: [ButtonComponent, FormsModule] // ✅ Adicionar FormsModule aqui
})
export class PaymentComponent {
  nomeCartao: string = '';
  numeroCartao: string = '';
  validade: string = '';
  cvv: string = '';

  constructor(private paymentService: PaymentService, private router: Router) {}

  finalizarCompra() {
    const paymentData = {
      userInfo: {
        userId: 1, // substituir por ID do usuário logado
        nome: this.nomeCartao,
        email: 'usuario@email.com' // substituir pelo email do usuário logado
      },
      paymentMethod: 'CARD',
      amount: 378.00, // valor do pedido
      status: 'PENDING'
    };

    this.paymentService.processPayment(paymentData).subscribe({
      next: (res) => {
        console.log('Pagamento confirmado', res);
        this.router.navigate(['/confirmacao']); // redirecionar para página de confirmação
      },
      error: (err) => {
        console.error('Erro no pagamento', err);
        alert('Erro ao processar o pagamento.');
      }
    });
  }
}
