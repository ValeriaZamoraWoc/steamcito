import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerPerfilEmpresa } from './ver-perfil-empresa';

describe('VerPerfilEmpresa', () => {
  let component: VerPerfilEmpresa;
  let fixture: ComponentFixture<VerPerfilEmpresa>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerPerfilEmpresa]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerPerfilEmpresa);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
