import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  logged = localStorage.getItem('user') !== null;
  user = JSON.parse(localStorage.getItem('user') || '{}')   

  ngOnInit(){
    console.log(this.logged)
    console.log(this.user)
  }
}