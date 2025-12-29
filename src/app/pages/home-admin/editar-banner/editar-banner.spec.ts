import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarBanner } from './editar-banner';

describe('EditarBanner', () => {
  let component: EditarBanner;
  let fixture: ComponentFixture<EditarBanner>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarBanner]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarBanner);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
