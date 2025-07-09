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
  cursosDoUsuario: any[] = [];

  constructor(private authService: AuthService) {}
  
  private cursoService = inject(CursosService);

  getThumbnailUrl(videoUrl: string): string {
    if (!videoUrl) return '';
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

  adicionarAoCarrinho(id:any,nome:any, preco:any) {
  
    const curso = {id, nome, preco}
    const carrinhoString = localStorage.getItem('carrinho');

  // Se já tiver itens, parseia; se não, começa com array vazio
  let carrinho: any[] = carrinhoString ? JSON.parse(carrinhoString) : [];

  // Adiciona o novo item
  carrinho.push(curso);

  // Salva de volta no localStorage
  localStorage.setItem('carrinho', JSON.stringify(carrinho));

  console.log('Item adicionado ao carrinho:', curso);
}

  ngOnInit(): void {
    this.authService.getUserObs().subscribe({
      next: (res) => {
        this.usuario = res;
        console.log('Usuário logado:', this.usuario);
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
        console.log("cursos", this.cursos);
      }
    })

    this.cursoService.getCursosUsuario().subscribe({
      next: (cursos) => {
        console.log('cursos do user', cursos );
        cursos.forEach((curso: any) => {
          this.cursoService.getAulasCurso(curso.id).subscribe({
            next: (res) => {
              console.log('deveria funcionar', res)
              curso.contentURL = res[0].contentURL
            }
          })
        })
        this.cursosDoUsuario = cursos;
    }});

  }

}