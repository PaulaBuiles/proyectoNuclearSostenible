import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../service/auth.service'; // Asegúrate de que el path sea correcto

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;

  constructor(private authService: AuthService) { }

  async ngOnInit(): Promise<void> {
    await this.checkLoginStatus();
    this.authService.isLoggedIn().subscribe(status => {
      this.isLoggedIn = status;
    });
  }

  async checkLoginStatus(): Promise<void> {
    const status = await this.authService.isLoggedIn().toPromise();
    this.isLoggedIn = status ?? false;

  }
}
