import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubirJuego } from './subir-juego';

describe('SubirJuego', () => {
  let component: SubirJuego;
  let fixture: ComponentFixture<SubirJuego>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubirJuego]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubirJuego);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
