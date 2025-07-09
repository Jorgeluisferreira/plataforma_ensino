import { Component, inject } from '@angular/core';
import { MenuLateralComponent } from '../components/menu-lateral/menu-lateral.component';
import { CommonModule } from '@angular/common';
import { ItemComponent } from '../components/item/item.component';
import { AuthService } from '../services/auth.service';
import { ReactiveFormsModule } from '@angular/forms'; 
import { HeaderComponent } from '../components/header/header.component';
import { FormsModule } from '@angular/forms';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { CursosService } from '../services/cursos.service';

@Component({
  selector: 'app-area-usuario',
  imports: [CommonModule, MenuLateralComponent, FormsModule,ReactiveFormsModule, ItemComponent, HeaderComponent],
  templateUrl: './area-usuario.component.html',
  styleUrl: './area-usuario.component.css'
})
export class AreaUsuarioComponent {
  selectedSection: string = 'cursos'; // padrão inicial

  selectSection(section: string): void {
    this.selectedSection = section;
  }

  modoEdicao = false;
  isAluno = false;
  usuario: any;
  cursosDoUsuario: any[] = [];
  usuarioBackup: any = {}; // Para cancelar edições
  cursosConcluidos = this.cursosDoUsuario.filter(curso => curso.progressoPercentual === 100);
  cursosTeste: any;

  constructor(private authService: AuthService) {}

  private cursoService = inject(CursosService);

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

  // area professor
  modoNovoCurso = false;
  cursoEditando: any = null;

  cursoForm = new FormGroup({
    nome: new FormControl('', Validators.required),
    descricao: new FormControl('', Validators.required),
    preco: new FormControl('', Validators.required),
    aulas: new FormArray([])
  });

  get aulas(): FormArray {
    return this.cursoForm.get('aulas') as FormArray;
  }

  adicionarAula() {
    const aula = new FormGroup({
      nome: new FormControl('', Validators.required),
      descricao: new FormControl('', Validators.required),
      contentURL: new FormControl('', Validators.required)
    });

    this.aulas.push(aula);
  }

  removerAula(index: number) {
    this.aulas.removeAt(index);
  }

  cadastrarCurso() {
  if (this.cursoForm.valid) {
    const dados = this.cursoForm.value;
    const aulas = this.cursoForm.get('aulas')?.value || [];

    const curso = { ...this.cursoForm.value };
    delete curso.aulas;

    console.log('Enviando curso:', curso);

    this.cursoService.cadastrarCurso(curso).subscribe({
      next: (res) => {
        const parsed = typeof res === 'string' ? JSON.parse(res) : res;
        console.log('Curso cadastrado com sucesso:', parsed);
        const idCurso = parsed.id;
        this.assinarCurso(idCurso);

        const aulasComId = aulas.map((aula: any) => ({
          curso_id: idCurso,
          ...aula
        }));''


        aulasComId.forEach((aula: any) => {
          console.log(aula)
          this.cadastrarAula(aula, idCurso);
        });
      },
      error: (err) => {
        console.error('Erro ao cadastrar curso:', err);
      }
    });
  } else {
    console.warn("Formulário inválido");
  }
}


  cadastrarAula(aula: any, id: any){
    this.cursoService.cadastrarAula(aula,id).subscribe({
      next: (res) => {
        console.log('aula Cadastrada com sucesso:', res);
      },
      error: (err) => {
        console.error('Erro ao cadastrar aula:', err);
      }
    });
  }

  assinarCurso(id:any){
    this.cursoService.assinarCurso(id).subscribe({
      next: (res) => {
        console.log(res);
      },
      error: (err) => {
        console.error('erro ao assinar', err)
      }
    })
  }

  getThumbnailUrl(videoUrl: string): string {
    const match = videoUrl.match(/(?:embed\/|v=)([^?&]+)/);
    const videoId = match ? match[1] : null;
    return videoId ? `https://img.youtube.com/vi/${videoId}/hqdefault.jpg` : '';
  }

  // gerarCertificado(cursoId: any,tipo: any){
  //   this.cursoService.gerarCertificado(cursoId, tipo).subscribe({
  //     next: (res) => {
  //       if (tipo === 'pdf') {
  //       // PDF: download
  //       const blob = new Blob([res], { type: 'application/pdf' });
  //       const url = window.URL.createObjectURL(blob);
  //       const a = document.createElement('a');
  //       a.href = url;
  //       a.download = `certificado_${cursoId}.pdf`;
  //       document.body.appendChild(a);
  //       a.click();
  //       document.body.removeChild(a);
  //       window.URL.revokeObjectURL(url);
  //     } else if (tipo === 'html') {
  //       // HTML: abrir em nova aba
  //       const blob = new Blob([res], { type: 'text/html' });
  //       const url = window.URL.createObjectURL(blob);
  //       window.open(url, '_blank');
  //       // Opcional: revogar depois de um tempo
  //       setTimeout(() => window.URL.revokeObjectURL(url), 10000);
  //     }
  //   }
  //   });
  // }

  certificadoHtml: string | null = null;
certificadoCursoId: number | null = null;

abrirCertificadoHtml(cursoId: number) {
  this.cursoService.gerarCertificado(cursoId, 'html').subscribe({
  next: (res) => {
    if (res instanceof Blob) {
      res.text().then(html => {
        this.certificadoHtml = html;
        this.certificadoCursoId = cursoId;
      });
    } else {
      this.certificadoHtml = res;
      this.certificadoCursoId = cursoId;
    }
  }
});
}

fecharModalCertificado() {
  this.certificadoHtml = null;
  this.certificadoCursoId = null;
}

baixarCertificadoPdf() {
  if (!this.certificadoCursoId) return;
  this.cursoService.gerarCertificado(this.certificadoCursoId, 'pdf').subscribe({
    next: (res) => {
      const blob = new Blob([res], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `certificado_${this.certificadoCursoId}.pdf`;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      window.URL.revokeObjectURL(url);
    }
  });
}

  ngOnInit(): void {

    this.cursoService.getAulas().subscribe({
      next: (res) => {
        console.log('aulas', res );
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

     

     this.authService.getUser().subscribe({
      next: (res) => {
        console.log('Usuário logado:', res);
        this.usuario = res;
        localStorage.setItem('usuario', JSON.stringify(this.usuario));
        if(this.usuario.roles == 'Aluno'){
          this.isAluno = true;
        }
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
