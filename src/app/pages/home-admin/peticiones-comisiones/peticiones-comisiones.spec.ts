import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeticionesComisiones } from './peticiones-comisiones';

describe('PeticionesComisiones', () => {
  let component: PeticionesComisiones;
  let fixture: ComponentFixture<PeticionesComisiones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PeticionesComisiones]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PeticionesComisiones);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
