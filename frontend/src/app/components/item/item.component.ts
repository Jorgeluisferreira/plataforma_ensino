import { Component, input, Input } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'app-item',
  imports: [ButtonComponent],
  templateUrl: './item.component.html',
  styleUrl: './item.component.css'
})
export class ItemComponent {
  @Input() titulo = '';
  @Input() descricao = '';
  @Input() link = '';
  @Input() botao ='';
}
