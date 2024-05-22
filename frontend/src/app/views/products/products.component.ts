import { Component } from '@angular/core';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {
  products = [
    { title: 'Producto 1', description: 'Descripción del Producto 1', price: 10.99, imageUrl: 'https://img.freepik.com/foto-gratis/colores-arremolinados-interactuan-danza-fluida-sobre-lienzo-que-muestra-tonos-vibrantes-patrones-dinamicos-que-capturan-caos-belleza-arte-abstracto_157027-2892.jpg' },
    { title: 'Producto 2', description: 'Descripción del Producto 2', price: 19.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
    { title: 'Producto 3', description: 'Descripción del Producto 3', price: 19.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
    { title: 'Producto 4', description: 'Descripción del Producto 4', price: 29.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
    { title: 'Producto 5', description: 'Descripción del Producto 5', price: 29.99, imageUrl: 'https://cdn.pixabay.com/photo/2024/02/26/19/39/monochrome-image-8598798_640.jpg' },
  ];

  filteredProducts = [...this.products];
  searchQuery: string = '';

  onSearch() {
    this.filteredProducts = this.products.filter(product =>
      product.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
      product.description.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }
  categoryOptions = [
    { value: 'all', text: 'All Categories' },
    { value: 'option1', text: 'Category 1' },
    { value: 'option2', text: 'Category 2' },
    { value: 'option3', text: 'Category 3' }
  ];

  // Opciones para el select de mes
  monthOptions = [
    { value: 'all', text: 'All Months' },
    { value: 'jan', text: 'January' },
    { value: 'feb', text: 'February' },
    { value: 'mar', text: 'March' }
  ];

  // Opciones para el select de estado
  statusOptions = [
    { value: 'all', text: 'All Statuses' },
    { value: 'available', text: 'Available' },
    { value: 'sold', text: 'Sold' },
    { value: 'outofstock', text: 'Out of Stock' }
  ];

  // Método para manejar el cambio en el select
  onFilterChange(filterType: string, value: string) {
    // Implementa lógica para filtrar los productos según el tipo de filtro y el valor seleccionado
    console.log(`Filter Type: ${filterType}, Value: ${value}`);
  }
}