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
  isAluno = true
  isLogged = false

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
  const dados = localStorage.getItem('usuario');
  if(dados){
    const usuario = JSON.parse(dados);
    if(usuario){
      this.isLogged = true
    }
  }
  }

  logout(){
    console.log("clicou")
    this.authService.logout();
    localStorage.removeItem('usuario');

  }
}
