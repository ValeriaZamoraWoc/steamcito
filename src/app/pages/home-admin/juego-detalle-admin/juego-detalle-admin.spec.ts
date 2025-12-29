import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuegoDetalleAdmin } from './juego-detalle-admin';

describe('JuegoDetalleAdmin', () => {
  let component: JuegoDetalleAdmin;
  let fixture: ComponentFixture<JuegoDetalleAdmin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JuegoDetalleAdmin]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JuegoDetalleAdmin);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
