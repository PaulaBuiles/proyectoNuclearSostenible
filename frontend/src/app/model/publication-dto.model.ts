export interface PublicationDto {
    idPublication: number;
    ownerId: number;
    productId: number;
    title: string;
    dateCreated: Date;
    state: {
      idState: number,
      description: string
  };
  }
  