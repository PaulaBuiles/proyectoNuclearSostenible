export interface AuthResponse {
  authenticationResponseDto: {
    token: string;
  };
  statusDto: {
    // Propiedades del estado
  };
  user: {
    idUser: number;
    username: string;
  };
}
