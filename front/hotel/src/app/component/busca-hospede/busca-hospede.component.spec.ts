import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuscaHospedeComponent } from './busca-hospede.component';

describe('BuscaHospedeComponent', () => {
  let component: BuscaHospedeComponent;
  let fixture: ComponentFixture<BuscaHospedeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuscaHospedeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuscaHospedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
