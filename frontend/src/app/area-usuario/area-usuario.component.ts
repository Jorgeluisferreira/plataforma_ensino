import { Component } from '@angular/core';
import { MenuLateralComponent } from '../components/menu-lateral/menu-lateral.component';
import { CommonModule } from '@angular/common';
import { ItemComponent } from '../components/item/item.component';
import { AuthService } from '../services/auth.service';
import { HeaderComponent } from '../components/header/header.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-area-usuario',
  imports: [CommonModule, MenuLateralComponent, FormsModule, ItemComponent, HeaderComponent],
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
    progressoPercentual: 100
  }
  ];

  modoEdicao = false;
  usuario: any;
  cursosUsuario: any[] = [];
  usuarioBackup: any = {}; // Para cancelar edições
  cursosConcluidos = this.cursosDoUsuario.filter(curso => curso.progressoPercentual === 100);

  constructor(private authService: AuthService) {}

  habilitarEdicao() {
  this.usuarioBackup = { ...this.usuario };
  this.modoEdicao = true;
  }

  cancelarEdicao() {
    this.usuario = { ...this.usuarioBackup };
    this.modoEdicao = false;
  }

  salvarAlteracoes() {
    // Lógica para salvar no backend aqui
    console.log('Dados salvos:', this.usuario);
    this.modoEdicao = false;
  }

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
          this.authService.logout();
        } else {
          console.error('Erro ao carregar usuário:', err);
        }
      }
    });
  }
}
