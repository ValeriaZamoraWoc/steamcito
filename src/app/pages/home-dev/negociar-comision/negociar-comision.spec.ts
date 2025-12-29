import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NegociarComision } from './negociar-comision';

describe('NegociarComision', () => {
  let component: NegociarComision;
  let fixture: ComponentFixture<NegociarComision>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NegociarComision]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NegociarComision);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
