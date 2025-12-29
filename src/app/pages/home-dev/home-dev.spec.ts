import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeDev } from './home-dev';

describe('HomeDev', () => {
  let component: HomeDev;
  let fixture: ComponentFixture<HomeDev>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeDev]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeDev);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
