import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  imports: [RouterLink, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isAluno = true
  isLogged = false

  constructor(private authService: AuthService) {}


  ngOnInit(): void {
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
