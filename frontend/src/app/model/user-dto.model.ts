export interface UserDto {
    idUser: number;
    userName: string;
    fullName: string;
    email: string;
    phone: string;
    password: string;
    typeIdUserId: number; // Refleja el ID del TypeId
    identification: string;
    image: string;
    description: string;
    status: boolean;
    isAdmin: boolean;
    points: number;
  }
  