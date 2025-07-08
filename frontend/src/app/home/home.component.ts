import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ItemComponent } from '../components/item/item.component';
import { AuthService } from '../services/auth.service';
import { HeaderComponent } from '../components/header/header.component';
import { RouterLink } from '@angular/router';
import { CursosService } from '../services/cursos.service';

@Component({
  selector: 'app-home',
  imports: [CommonModule, ItemComponent, HeaderComponent, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  usuario: any; // ou crie um tipo/interface se preferir

  cursos: any[] = [];

  constructor(private authService: AuthService) {}
  
  private cursoService = inject(CursosService);

  getThumbnailUrl(videoUrl: string): string {
    const match = videoUrl.match(/(?:embed\/|v=)([^?&]+)/);
    const videoId = match ? match[1] : null;
    return videoId ? `https://img.youtube.com/vi/${videoId}/hqdefault.jpg` : '';
  }

  assinarCurso(idCurso: any){
    return this.cursoService.assinarCurso(idCurso).subscribe({
      next: (res) => {
        console.log(res)
      }
    })
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

    this.cursoService.getCursos().subscribe({
      next: (res) => {
        res.forEach((curso: any) => {
          this.cursoService.getAulasCurso(curso.id).subscribe({
            next: (res) => {
              console.log('deveria funcionar', res)
              curso.contentURL = res[0].contentURL
            }
          })
        })
        this.cursos = res
        console.log(res)
      }
    })


  }

}