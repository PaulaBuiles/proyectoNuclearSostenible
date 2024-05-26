export interface ReportEntity {
    idReport?: number;
    description: string;
    denounced: UserDto;
    complainant: UserDto;
  }
  
  export interface UserDto {
    idUser: number;
    fullName: string;
  }
  