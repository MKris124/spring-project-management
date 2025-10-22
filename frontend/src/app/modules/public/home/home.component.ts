import { Component, OnInit } from '@angular/core';
import { PublicService } from '../services/public.service';
import { CommonModule } from '@angular/common';
import { StorageService } from '../../../auth/services/storage/storage.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CustomerService } from '../../customer/services/customer.service';
import { OfferDto } from '../../../models/offer.dto';
import { AddOfferComponent } from '../../customer/components/add-offer/add-offer.component';
import { MatDialog } from '@angular/material/dialog';
import { OrderDto } from '../../../models/order.dto';
import { AddOrderComponent } from '../../customer/components/add-order/add-order.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  runningProjects: any[] = [];
  rejectedOrders: any[] = [];
  rejectedOffers: any[] = [];
  isCustomerLoggedIn: boolean=StorageService.isCustomerLoggedIn();
  
  constructor(
    private publicService: PublicService,
    private snackbar: MatSnackBar,
    private customerService: CustomerService,
    private dialog: MatDialog,
  ) {}

  ngOnInit(): void {
    this.loadRunningProjects();
    

  if (this.isCustomerLoggedIn) {
    const customerId = Number(StorageService.getUserId());
    this.customerService.getRejectedOrders(customerId).subscribe({
      next: (orders) => {
        this.rejectedOrders = orders.map((order:any) => order.projectId);
      },
      error: (err) => console.error('Error fetching rejected orders:', err)
    });

    this.customerService.getRejectedOffers(customerId).subscribe({
      next: (offers) => {
        this.rejectedOffers = offers.map((offer:any) => offer.projectId);
      },
      error: (err) => console.error('Error fetching rejected offers:', err)
    });
  }
  }

  loadRunningProjects(): void {
    this.publicService.getRunningProjects().subscribe({
      next: (projects) => {
        this.runningProjects = projects;
      },
      error: (err) => {
        console.error('Error loading running projects:', err);
      }
    });
  }

  
  isOfferPlaced(projectId: number): boolean {
    return localStorage.getItem(`offerPlaced_${projectId}`) === 'true';
  }
  isOrderPlaced(projectId: number): boolean {
    return localStorage.getItem(`orderPlaced_${projectId}`) === 'true';
  }
  

  
  placeOffer(projectId: number): void {
    if (localStorage.getItem(`offerPlaced_${projectId}`) === 'true') {
      this.snackbar.open('You or someone has/have already placed an offer for this project.', '', {
        duration: 5000
      });
      return;
    }

    if (!this.isCustomerLoggedIn) {
      this.snackbar.open('You have to log in to add an offer', 'Log in', {
        duration: 5000,
      }).onAction().subscribe(() => {
        window.location.href = '/login';
      });
      return;
    }

    const dialogRef = this.dialog.open(AddOfferComponent, {
      width: '400px',
      data: { projectId: projectId }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const offerDto: OfferDto = {
          id: 0,  
          projectId: projectId,
          customerId: Number(StorageService.getUserId()),
          offerDetails: result.offerDetails,
          status: "PENDING"
        };

        this.customerService.placeOffer(offerDto.customerId, projectId, offerDto).subscribe({
          next: () => {
            localStorage.setItem(`offerPlaced_${projectId}`, 'true');
            this.snackbar.open('Offer successfully placed!', '', {
              duration: 5000
            });
          },
          error: (err) => {
            console.error('Failed to place offer:', err);
            this.snackbar.open('Failed to place offer. Try again later.', 'Close', {
              duration: 5000
            });
          }
        });
      }
    });
  }

  placeOrder(projectId: number): void {
    if (localStorage.getItem(`orderPlaced_${projectId}`) === 'true') {
      this.snackbar.open('You or someone has/have already placed an order for this project.', 'Close', {
        duration: 5000
      });
      return;
    }
   
    if (!this.isCustomerLoggedIn) {
      this.snackbar.open('You have to log in to place an order', 'Log in', {
        duration: 5000,
      }).onAction().subscribe(() => {
        window.location.href = '/login';
      });
      return;
    }

 
    const dialogRef = this.dialog.open(AddOrderComponent, {
      width: '400px',
      data: { projectId: projectId }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const orderDto: OrderDto = {
          id: 0,  
          projectId: projectId,
          customerId: Number(StorageService.getUserId()),
          details: result.orderDetails,
          status: "PENDING"
        };

        this.customerService.placeOrder(orderDto.customerId, projectId, orderDto).subscribe({
          next: () => {
            localStorage.setItem(`orderPlaced_${projectId}`, 'true');
            this.snackbar.open('Order successfully placed!', '', {
              duration: 5000
            });
          },
          error: (err) => {
            console.error('Failed to place order:', err);
            this.snackbar.open('Failed to place order. Try again later.', '', {
              duration: 5000
            });
          }
        });
      }
    });
  }


}
