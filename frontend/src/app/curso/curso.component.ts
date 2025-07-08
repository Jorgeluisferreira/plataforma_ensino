import { Component, inject, OnInit } from '@angular/core';
import { HeaderComponent } from '../components/header/header.component';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { SafeUrlPipe } from '../pipes/safe-url.pipe';
import { CommonModule } from '@angular/common';
import { CursosService } from '../services/cursos.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-curso',
  imports: [ HeaderComponent, SafeUrlPipe, CommonModule],
  templateUrl: './curso.component.html',
  styleUrl: './curso.component.css'
})
export class CursoComponent {
  aulas: any[] = [];

  aulaSelecionada = 0;

  get aulaAtual() {
  const aula = this.aulas[this.aulaSelecionada] ?? this.aulas[0];
  return {
    ...aula,
    contentURL: this.sanitizar(aula.contentURL)
  };
}


  constructor(private sanitizer: DomSanitizer,
    private route: ActivatedRoute,  // Para acessar parâmetros
    private router: Router          // Para navegação programática
  ) {}

  private cursoService = inject(CursosService);

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');
    this.cursoService.getAulasCurso(id).subscribe({
      next: (res) => {
        console.log('aulas', res );
        this.aulas = res
      }
    })
  }

  

  trocarAula(index: number): void {
    this.aulaSelecionada = index;
  }

  sanitizar(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}

