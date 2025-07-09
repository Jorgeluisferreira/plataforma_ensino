import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-registration',
  imports: [CommonModule, FormsModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {
  isRegistering = false;

  toggleForm(){
    this.isRegistering = !this.isRegistering;
  }

  nome='';
  email='';
  senha='';
  roles=1;

  users: any[] = [];

  constructor(private authServise: AuthService,
    private router : Router
  ) {}

  ngOnInit(){
    this.loadUsers();
  }

  loadUsers(){
    this.authServise.getUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (error) => {
        console.error('erro ao carregar usuarios', error);
      }
    });
  }

  sendData(){
    if(this.isRegistering){
      this.cadastrar()
    }else{
      this.login()
    }
  }

  cadastroProf(){
    const newUser = {
      nome: this.nome,
      email: this.email,
      senha: this.senha,
      roles: 2
    };
    this.authServise.createProf(newUser).subscribe({
      next: () => {
        this.loadUsers();
        this.nome = '';
        this.email ='';
        this.senha ='';
        this.roles = 1;
      },
      error: (err) => console.error('Erro ao cadastrar professor:', err)
    });
  }

  cadastrar() {
    const newUser = {
      nome: this.nome,
      email: this.email,
      senha: this.senha,
      roles: this.roles
    };

    this.authServise.createUser(newUser).subscribe({
      next: () => {
        this.loadUsers();
        this.nome = '';
        this.email ='';
        this.senha ='';
        this.roles = 1;
      },
      error: (error) => console.error('Erro ao cadastrar:', error)
      }
  );
  }

  login(){
      this.authServise.login(this.email,this.senha).subscribe({
        next: (response) => {
          console.log('Login bem sucedido', response);
          localStorage.setItem('user',(response.user))
          this.router.navigate(['/'])
        },
        error: (error) =>{
          console.log('Falha no login', error);
          alert('Email ou senha invalidos')
        }
      })
  }

  deleteUser(){
    this.authServise.deleteUser(5).subscribe({
      next: () => this.loadUsers(),
      error: (error) => console.log(error)
    })
  }

}
