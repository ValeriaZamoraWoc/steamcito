import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorialGastosComponent } from './historial-gastos';

describe('HistorialGastos', () => {
  let component: HistorialGastosComponent;
  let fixture: ComponentFixture<HistorialGastosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistorialGastosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistorialGastosComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
