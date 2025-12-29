import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PantallaPrincipalComun } from './pantalla-principal-comun';

describe('PantallaPrincipalComun', () => {
  let component: PantallaPrincipalComun;
  let fixture: ComponentFixture<PantallaPrincipalComun>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PantallaPrincipalComun]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PantallaPrincipalComun);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
