import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;
  menuOpen: boolean = false;
  profileMenuOpen: boolean = false;
  productMenuOpen: boolean = false;
  publicationMenuOpen: boolean = false;
  username: string | null = null; // Variable para almacenar el nombre de usuario

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authService.isLoggedIn().subscribe((loggedIn: boolean) => {
      this.isLoggedIn = loggedIn;
      if (loggedIn) {
        // Si el usuario está logueado, obtenemos su nombre de usuario del localStorage
        this.username = localStorage.getItem('username');
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.isLoggedIn = false;
    this.router.navigate(['/login']);
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  toggleProfileMenu(event: Event): void {
    event.stopPropagation();
    this.profileMenuOpen = !this.profileMenuOpen;
    this.productMenuOpen = false;  // Cerrar el menú de productos si está abierto
    this.publicationMenuOpen = false;  // Cerrar el menú de publicaciones si está abierto
  }

  toggleProductMenu(event: Event): void {
    event.stopPropagation();
    this.productMenuOpen = !this.productMenuOpen;
    this.profileMenuOpen = false;  // Cerrar el menú de perfil si está abierto
    this.publicationMenuOpen = false;  // Cerrar el menú de publicaciones si está abierto
  }

  togglePublicationMenu(event: Event): void {
    event.stopPropagation();
    this.publicationMenuOpen = !this.publicationMenuOpen;
    this.profileMenuOpen = false;  // Cerrar el menú de perfil si está abierto
    this.productMenuOpen = false;  // Cerrar el menú de productos si está abierto
  }

  getUsername(): string {
    // Función para obtener el nombre de usuario
    return this.username || ''; // Si username es null, retorna un string vacío para evitar errores
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    if (this.profileMenuOpen || this.productMenuOpen || this.publicationMenuOpen) {
      this.profileMenuOpen = false;
      this.productMenuOpen = false;
      this.publicationMenuOpen = false;
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event): void {
    if ((event.target as Window).innerWidth > 850) {
      this.menuOpen = false;
    }
  }
}
