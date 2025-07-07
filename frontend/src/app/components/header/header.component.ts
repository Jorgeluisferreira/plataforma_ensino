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
  const fake = [{ nome: 'Curso Teste', preco: 29.99 },{nome: 'Curso 2', preco: 19.99}];
  localStorage.setItem('carrinho', JSON.stringify(fake));
  this.authService.currentUser$.subscribe(user => {
      this.isLogged = true
  });

  this.authService.getUser().subscribe({
      next: (res) => {
        this.usuario = res;
        localStorage.setItem('usuario', JSON.stringify(this.usuario));
        if(this.usuario.roles == 'Aluno'){
          this.isAluno = true;
        }
      },
      error: (err) => {
        if (err.status === 400 || err.status === 401) {
          console.log('Usuário não autenticado. Ignorando erro.');
          this.authService.logout();
        } else {
          console.error('Erro ao carregar usuário:', err);
        }
      }
    });
  }


  logout(){
    console.log("clicou")
    this.authService.logout();
    localStorage.removeItem('usuario');

  }
}
