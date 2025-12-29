import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuegoDetalleDev } from './juego-detalle-dev';

describe('JuegoDetalleDev', () => {
  let component: JuegoDetalleDev;
  let fixture: ComponentFixture<JuegoDetalleDev>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JuegoDetalleDev]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JuegoDetalleDev);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
