import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PantallaBibliotecaComun } from './pantalla-biblioteca-comun';

describe('PantallaBibliotecaComun', () => {
  let component: PantallaBibliotecaComun;
  let fixture: ComponentFixture<PantallaBibliotecaComun>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PantallaBibliotecaComun]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PantallaBibliotecaComun);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
