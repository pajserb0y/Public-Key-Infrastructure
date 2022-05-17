import { Component, Inject, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Certificate } from '../model/certificate';
@Component({
  selector: 'app-details-dialog',
  templateUrl: './details-dialog.component.html',
  styleUrls: ['./details-dialog.component.css']
})
export class DetailsDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: {certificate: Certificate}) { }

  ngOnInit(): void {
  }

}
