import { PublicationDto } from "./publication-dto.model";

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
    publication?: PublicationDto;
    productCategory?: {
      idCategory: number;
      title: string;
    };
  }

export interface UserDto {
  idUser: number;
  userName: string;

}
  