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
  isAluno = false;
  usuario: any;
  cursosUsuario: any[] = [];
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
    aulas: new FormArray([])
  });

  get aulas(): FormArray {
    return this.cursoForm.get('aulas') as FormArray;
  }

  adicionarAula() {
    const aula = new FormGroup({
      titulo: new FormControl('', Validators.required),
      duracao: new FormControl('', Validators.required)
    });

    this.aulas.push(aula);
  }

  removerAula(index: number) {
    this.aulas.removeAt(index);
  }

  cadastrarCurso() {
    if (this.cursoForm.valid) {
      const dados = this.cursoForm.value;
      const dadosParaEnviar = { ...this.cursoForm.value };
      delete dadosParaEnviar.aulas;
      console.log('Enviando curso:', dadosParaEnviar);

      this.cursoService.cadastrarCurso(dadosParaEnviar).subscribe({
        next: (res) => {
          console.log('Curso cadastrado com sucesso:', res);
        },
        error: (err) => {
          console.error('Erro ao cadastrar curso:', err);
        }
      });
  } else {
    console.warn("Formulário inválido");
  }
  }

  ngOnInit(): void {

    this.cursoService.getCursos().subscribe({
      next: (res) => {
        console.log('cursos', res );
        this.cursosTeste = res
      }
    })
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
