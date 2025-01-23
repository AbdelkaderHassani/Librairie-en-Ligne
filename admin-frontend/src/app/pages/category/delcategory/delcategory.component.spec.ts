import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DelcategoryComponent } from './delcategory.component';

describe('DelcategoryComponent', () => {
  let component: DelcategoryComponent;
  let fixture: ComponentFixture<DelcategoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DelcategoryComponent]
    });
    fixture = TestBed.createComponent(DelcategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
