import { Component } from '@angular/core';

import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './modules/shared/navbar/navbar.component';
import { LoginComponent } from './auth/components/login/login.component';
import { StorageService } from './auth/services/storage/storage.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  title = 'web';
  isAuthenticated :boolean = true;
  constructor(){}
  ngOnInit(){

   // const isAuthenticated:boolean = StorageService.isUserLoggedIn();
    //this.isLoggedIn = isAuthenticated;
  }
}
