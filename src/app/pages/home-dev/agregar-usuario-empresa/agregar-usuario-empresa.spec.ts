import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarUsuarioEmpresa } from './agregar-usuario-empresa';

describe('AgregarUsuarioEmpresa', () => {
  let component: AgregarUsuarioEmpresa;
  let fixture: ComponentFixture<AgregarUsuarioEmpresa>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgregarUsuarioEmpresa]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgregarUsuarioEmpresa);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
