import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionarClasificaciones } from './gestionar-clasificaciones';

describe('GestionarClasificaciones', () => {
  let component: GestionarClasificaciones;
  let fixture: ComponentFixture<GestionarClasificaciones>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionarClasificaciones]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionarClasificaciones);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
