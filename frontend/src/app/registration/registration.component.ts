import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../services/registration.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-registration',
  imports: [CommonModule, FormsModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent implements OnInit {
  isRegistering = true;

  toggleForm(){
    this.isRegistering = !this.isRegistering;
  }

  name='';
  email='';
  password='';
  birthday='';

  users: any[] = [];

  constructor(private userService: RegistrationService) {}

  ngOnInit(){
    this.loadUsers();
  }

  loadUsers(){
    this.userService.getUsers().subscribe({
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

  cadastrar() {
    const newUser = {
      name: this.name,
      email: this.email,
      password: this.password,
      birthday: this.birthday
    };

    this.userService.createUser(newUser).subscribe({
      next: () => {
        this.loadUsers();
        this.name = '';
        this.email ='';
        this.password ='';
        this.birthday ='';
      },
      error: (error) => console.error('Erro ao cadastrar:', error)
      }
  );
  }

  login(){

  }

  deleteUser(){
    this.userService.deleteUser(5).subscribe({
      next: () => this.loadUsers(),
      error: (error) => console.log(error)
    })
  }

}
