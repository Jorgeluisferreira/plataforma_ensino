import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemComponent } from '../components/item/item.component';
import { FeedbackComponent } from '../components/feedback/feedback.component';
import { AuthService } from '../services/auth.service';
import { HeaderComponent } from '../components/header/header.component';

@Component({
  selector: 'app-home',
  imports: [CommonModule, ItemComponent, FeedbackComponent, HeaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  usuario: any; // ou crie um tipo/interface se preferir

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getUser().subscribe({
      next: (res) => {
        console.log('Usuário logado:', res);
        this.usuario = res;
        localStorage.setItem('usuario', JSON.stringify(this.usuario));
      },
      error: (err) => {
        if (err.status === 400 || err.status === 401) {
          console.log('Usuário não autenticado. Ignorando erro.');
        } else {
          console.error('Erro ao carregar usuário:', err);
        }
      }
    });


  }

}