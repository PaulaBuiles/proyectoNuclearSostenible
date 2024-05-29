import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyProductOffersComponent } from './my-product-offers.component';

describe('MyProductOffersComponent', () => {
  let component: MyProductOffersComponent;
  let fixture: ComponentFixture<MyProductOffersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyProductOffersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MyProductOffersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
