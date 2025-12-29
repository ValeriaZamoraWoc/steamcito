import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpcionesReportes } from './opciones-reportes';

describe('OpcionesReportes', () => {
  let component: OpcionesReportes;
  let fixture: ComponentFixture<OpcionesReportes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpcionesReportes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OpcionesReportes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
