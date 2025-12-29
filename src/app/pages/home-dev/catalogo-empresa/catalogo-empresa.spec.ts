import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatalogoEmpresa } from './catalogo-empresa';

describe('CatalogoEmpresa', () => {
  let component: CatalogoEmpresa;
  let fixture: ComponentFixture<CatalogoEmpresa>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogoEmpresa]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CatalogoEmpresa);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
