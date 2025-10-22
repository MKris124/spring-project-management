import { Component, OnInit } from '@angular/core';
import { OrderDto } from '../../../../models/order.dto';
import { OfferDto } from '../../../../models/offer.dto';
import { CustomerService } from '../../services/customer.service';
import { CommonModule } from '@angular/common';
import { StorageService } from '../../../../auth/services/storage/storage.service';
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  acceptedOrders: OrderDto[] = [];
  customerOffers: OfferDto[] = [];

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    const customerId = Number(StorageService.getUserId());

    this.customerService.getCustomerOrders(customerId).subscribe({
      next: (orders) => {
        this.acceptedOrders = orders;
      },
      error: (err) => {
        console.error('Error fetching orders:', err);
      }
    });

    this.customerService.getCustomerOffers(customerId).subscribe({
      next: (offers) => {
        this.customerOffers = offers;
      },
      error: (err) => {
        console.error('Error fetching offers:', err);
      }
    });
  }
}
