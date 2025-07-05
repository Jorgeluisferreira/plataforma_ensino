import { Component, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isLogged = false
  isAluno = true

  ngOnInit(): void {
  const dados = localStorage.getItem('usuario');

  if(dados){
    const usuario = JSON.parse(dados);
    if(usuario){
      this.isLogged = true
    }
  }
  }
}
