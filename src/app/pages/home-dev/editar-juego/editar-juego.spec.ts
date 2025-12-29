import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarJuego } from './editar-juego';

describe('EditarJuego', () => {
  let component: EditarJuego;
  let fixture: ComponentFixture<EditarJuego>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarJuego]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarJuego);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
