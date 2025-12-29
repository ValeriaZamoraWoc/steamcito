import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportesDev } from './reportes-dev';

describe('ReportesDev', () => {
  let component: ReportesDev;
  let fixture: ComponentFixture<ReportesDev>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReportesDev]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportesDev);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
