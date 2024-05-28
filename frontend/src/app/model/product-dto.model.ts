export interface ProductDto {
    idProduct: number;
    name: string;
    price: number;
    imageUrl: string;
    description: string;
    userId: number;
    categoryId: number;
    status: boolean;
    user?: UserDto;
  }

export interface UserDto {
  idUser: number;
  userName: string;

}
  