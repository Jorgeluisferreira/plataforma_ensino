import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonComponent } from '../components/button/button.component';
import { PaymentService } from '../services/payment.service'
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CursosService } from '../services/cursos.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css'],
  imports: [ButtonComponent, FormsModule, CommonModule]
})
export class PaymentComponent {
  nomeCartao: string = '';
  numeroCartao: string = '';
  validade: string = '';
  cvv: string = '';
  metodoPagamento: string = 'credito';

  carrinho: any[] = [];
  total: any = '';
  constructor(private paymentService: PaymentService, private router: Router) {}

  private cursoService = inject(CursosService);

  assinarCurso(idCurso: any){
    return this.cursoService.assinarCurso(idCurso).subscribe({
      next: (res) => {
        console.log(res)
      }
    })
  }

  finalizarCompra() {
    const paymentData = {
      userInfo: {
        userId: 1,
        nome: this.nomeCartao,
        email: 'usuario@email.com' // preencher com email do usuário logado
      },
      paymentMethod: this.metodoPagamento,
      amount: this.total,
      status: 'PENDING'
    };

    this.paymentService.processPayment(paymentData).subscribe({
      next: (res) => {
        console.log('Pagamento confirmado', res);
        for(let item of this.carrinho){
          this.assinarCurso(item.id)
        }

        localStorage.removeItem('carrinho');
        this.router.navigate(['/obrigado']); // exemplo: página de confirmação
      },
      error: (err) => {
        console.error('Erro no pagamento', err);
        alert('Erro ao processar o pagamento.');
      }
    });
  }

  ngOnInit(): void {
    const dados = localStorage.getItem("carrinho")
    if(dados){
      this.carrinho = JSON.parse(dados);
      this.total = this.carrinho.reduce((soma, curso) => soma + curso.preco, 0);
      console.log(this.carrinho)
    }
  }
}
