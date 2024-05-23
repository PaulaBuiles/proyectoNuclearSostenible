import { Component } from '@angular/core';
import { RequestsService } from '../../../service/requests.service';
import { UserDto } from '../../../model/user-dto.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user = {
    name: 'John Doe',
    description: 'Desarrollador web con 5 años de experiencia en Angular.',
    rating: 5,
    profileImageUrl: 'https://img.freepik.com/foto-gratis/hombre-moreno-positiva-brazos-cruzados_1187-5797.jpg', // URL de la imagen del perfil
    products: [
      { title: 'Producto 1', description: 'Descripción del Producto 1', price: 10.99, imageUrl: 'https://img.freepik.com/foto-gratis/colores-arremolinados-interactuan-danza-fluida-sobre-lienzo-que-muestra-tonos-vibrantes-patrones-dinamicos-que-capturan-caos-belleza-arte-abstracto_157027-2892.jpg' },
      { title: 'Producto 2', description: 'Descripción del Producto 2', price: 19.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
      { title: 'Producto 3', description: 'Descripción del Producto 3', price: 19.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
      { title: 'Producto 4', description: 'Descripción del Producto 4', price: 29.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
      { title: 'Producto 5', description: 'Descripción del Producto 5', price: 29.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
    ]
  };

  filteredProducts = [...this.user.products];

  

  user1: UserDto | null = null;

  constructor(private requestsService: RequestsService) {}

  ngOnInit(): void {
    const userId = 1; // Por ejemplo, puedes obtener este ID dinámicamente
    this.requestsService.getUserById(userId).subscribe(
      (user: UserDto) => {
        this.user1 = user;
        console.log(user);
      },
      error => {
        console.error('Error fetching user', error);
      }
    );
  }
  
}

