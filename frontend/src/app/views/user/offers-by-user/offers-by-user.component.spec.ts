import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffersByUserComponent } from './offers-by-user.component';

describe('OffersByUserComponent', () => {
  let component: OffersByUserComponent;
  let fixture: ComponentFixture<OffersByUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OffersByUserComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OffersByUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
