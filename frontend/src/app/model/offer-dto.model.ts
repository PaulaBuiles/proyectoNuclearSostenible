export interface OfferDto {
    idOffer: number;
    publicationId: number;
    exchangedProductId: number;
    offererId: number;
    monetaryValue: number;
    offerDate: Date;
    transactionId: number;
    state: string;
    offererName: string;
    offererPhone: string;
  }
  