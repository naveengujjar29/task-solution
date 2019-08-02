import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import {
  MatButtonModule,
  MatIconModule, MatDialog,
  MatDialogModule, MatInputModule,
  MatSelectModule, MatOptionModule,
  MatChip, MatChipsModule,
  MatAutocompleteModule
} from '@angular/material';
import { NgModule } from '@angular/core';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  exports: [
    MatCardModule,
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatChipsModule,
    MatIconModule,
    MatAutocompleteModule
  ]
})
export class AppMaterialModule { }
