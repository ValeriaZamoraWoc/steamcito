import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoWallet } from './estado-wallet';

describe('EstadoWallet', () => {
  let component: EstadoWallet;
  let fixture: ComponentFixture<EstadoWallet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoWallet]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadoWallet);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
