import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-header',
  imports: [RouterLink, CommonModule, ButtonComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isAluno = false
  isLogged = false
  usuario: any = null;

  constructor(private authService: AuthService) {}

  carrinhoAberto = false;
  itensCarrinho: any[] = [];

  abrirCarrinho(): void {
    this.itensCarrinho = JSON.parse(localStorage.getItem('carrinho') || '[]');
    this.carrinhoAberto = true;
  }

  fecharCarrinho(): void {
    this.carrinhoAberto = false;
  }

  removerItem(index: number): void {
  this.itensCarrinho.splice(index, 1); // remove visualmente
  localStorage.setItem('carrinho', JSON.stringify(this.itensCarrinho)); // atualiza armazenamento
  }

  ngOnInit(): void {

  this.authService.getUserObs().subscribe({
    next: (res) => {
      this.usuario = res;
      this.isLogged = !!this.usuario; // Verifica se o usuário está logado
      if (this.usuario && this.usuario.roles && this.usuario.roles.includes('Aluno')) {
        this.isAluno = true;
      } else {
        this.isAluno = false;
      }
      console.log('Usuário logado:', this.usuario, this.isLogged, this.isAluno);

    }
  });
  }


  logout(){
    console.log("clicou")
    this.authService.logout();
    localStorage.removeItem('usuario');

  }
}
