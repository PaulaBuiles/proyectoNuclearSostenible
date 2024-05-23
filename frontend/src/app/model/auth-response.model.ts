export interface AuthResponse {
  authenticationResponseDto: {
    token: string;
  };
  user: {
    // Propiedades del usuario
  };
  statusDto: {
    // Propiedades del estado
  };
}
