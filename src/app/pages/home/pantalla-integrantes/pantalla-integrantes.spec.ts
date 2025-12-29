import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PantallaIntegrantes } from './pantalla-integrantes';

describe('PantallaIntegrantes', () => {
  let component: PantallaIntegrantes;
  let fixture: ComponentFixture<PantallaIntegrantes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PantallaIntegrantes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PantallaIntegrantes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
