import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroGeneral } from './registro-general';

describe('RegistroGeneral', () => {
  let component: RegistroGeneral;
  let fixture: ComponentFixture<RegistroGeneral>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistroGeneral]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistroGeneral);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
