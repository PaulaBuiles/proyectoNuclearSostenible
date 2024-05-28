import { TestBed } from '@angular/core/testing';

import { PublicationInfoService } from './publication-info.service';

describe('PublicationInfoService', () => {
  let service: PublicationInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PublicationInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
