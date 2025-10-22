import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleManagemetComponent } from './role-managemet.component';

describe('RoleManagemetComponent', () => {
  let component: RoleManagemetComponent;
  let fixture: ComponentFixture<RoleManagemetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RoleManagemetComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoleManagemetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
