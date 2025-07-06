import { Component } from '@angular/core';
import { MenuLateralComponent } from '../components/menu-lateral/menu-lateral.component';
import { CommonModule } from '@angular/common';
import { ItemComponent } from '../components/item/item.component';
import { AuthService } from '../services/auth.service';
import { HeaderComponent } from '../components/header/header.component';

@Component({
  selector: 'app-area-usuario',
  imports: [CommonModule, MenuLateralComponent, ItemComponent, HeaderComponent],
  templateUrl: './area-usuario.component.html',
  styleUrl: './area-usuario.component.css'
})
export class AreaUsuarioComponent {
  selectedSection: string = 'cursos'; // padrão inicial

  selectSection(section: string): void {
    this.selectedSection = section;
  }

  cursosDoUsuario = [
  {
    id: 1,
    nome: 'Curso Angular Básico',
    imagemUrl: 'https://www.diariodeinvestimentos.com.br/wp-content/uploads/2021/09/thiago-nigro-primo-rico.jpg',
    progressoPercentual: 40
  },
  {
    id: 2,
    nome: 'Curso de Node.js',
    imagemUrl: 'https://static.poder360.com.br/2024/09/pablo-marcal-retrato-848x477.jpg',
    progressoPercentual: 75
  }
  ];


  usuario: any;
  cursosUsuario: any[] = [];

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getUser().subscribe({
      next: (res) => {
        this.usuario = res;
        localStorage.setItem('usuario', JSON.stringify(this.usuario));

        // Simulando cursos do usuário (pode puxar de uma API depois)
        this.cursosUsuario = [
          { titulo: 'Angular Completo', descricao: 'Aprenda Angular do zero', progresso: 75 },
          { titulo: 'Node.js API', descricao: 'Construindo APIs REST', progresso: 40 },
          { titulo: 'UX/UI Design', descricao: 'Fundamentos de design de interface', progresso: 90 }
        ];
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
