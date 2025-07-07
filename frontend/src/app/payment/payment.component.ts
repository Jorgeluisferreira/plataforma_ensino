import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonComponent } from '../components/button/button.component';
import { PaymentService } from '../services/payment.service'
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
  imports: [ButtonComponent, FormsModule]
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
        userId: 1, // ID do usuário logado, trocar pelo valor correto
        nome: this.nomeCartao,
        email: 'usuario@email.com' // preencher com email do usuário logado
      },
      paymentMethod: 'CARD',
      amount: 378.00, // ajustar para valor do pedido real
      status: 'PENDING'
    };

    this.paymentService.processPayment(paymentData).subscribe({
      next: (res) => {
        console.log('Pagamento confirmado', res);
        // Aqui você pode navegar para outra página ou mostrar mensagem
        this.router.navigate(['/confirmacao']); // exemplo: página de confirmação
      },
      error: (err) => {
        console.error('Erro no pagamento', err);
        alert('Erro ao processar o pagamento.');
      }
    });
  }
}
