import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../components/header/header.component';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { SafeUrlPipe } from '../pipes/safe-url.pipe';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-curso',
  imports: [ HeaderComponent, SafeUrlPipe, CommonModule],
  templateUrl: './curso.component.html',
  styleUrl: './curso.component.css'
})
export class CursoComponent {
  aulas = [
    {
      titulo: 'Aula 1 – RockLee vs Gaara',
      descricao: 'o melhor video de todos.',
      videoUrl: 'https://www.youtube.com/embed/VgDgWzBL7s4?si=tN8wqao_qYGtxEtk'
    },
    {
      titulo: 'Aula 2 – rock lee vs gaara dnv',
      descricao: 'pq sim.',
      videoUrl: 'https://www.youtube.com/embed/VgDgWzBL7s4?si=tN8wqao_qYGtxEtk'
    },
    {
      titulo: 'Aula 3 – O retorno do rei',
      descricao: 'achou que tinha acabado ?',
      videoUrl: 'https://www.youtube.com/embed/VgDgWzBL7s4?si=tN8wqao_qYGtxEtk'
    }
  ];

  aulaSelecionada = 0;

  get aulaAtual() {
  const aula = this.aulas[this.aulaSelecionada] ?? this.aulas[0];
  return {
    ...aula,
    videoUrl: this.sanitizar(aula.videoUrl)
  };
}


  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit(): void {}

  trocarAula(index: number): void {
    this.aulaSelecionada = index;
  }

  sanitizar(url: string): SafeResourceUrl {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}

