import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ItemComponent } from './components/item/item.component';
import { FeedbackComponent } from './components/feedback/feedback.component';
import { MenuLateralComponent } from './components/menu-lateral/menu-lateral.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent,FormsModule, ItemComponent, FeedbackComponent, MenuLateralComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
 
}
