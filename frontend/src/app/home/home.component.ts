import { Component } from '@angular/core';
import { ItemComponent } from '../components/item/item.component';
import { FeedbackComponent } from '../components/feedback/feedback.component';

@Component({
  selector: 'app-home',
  imports: [ItemComponent, FeedbackComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}