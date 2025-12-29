import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PantallaGruposFamiliares } from './pantalla-grupos-familiares';

describe('PantallaGruposFamiliares', () => {
  let component: PantallaGruposFamiliares;
  let fixture: ComponentFixture<PantallaGruposFamiliares>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PantallaGruposFamiliares]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PantallaGruposFamiliares);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
