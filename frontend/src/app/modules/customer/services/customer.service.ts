import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { StorageService } from '../../../auth/services/storage/storage.service';
import { ProjectDto } from '../../../models/project.dto';
import { Observable } from 'rxjs';
import { OfferDto } from '../../../models/offer.dto';
import { OrderDto } from '../../../models/order.dto';

const BASE_URL = "http://localhost:8080/api/customer";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http:HttpClient) { }
  
  placeOrder(customerId: number, projectId: number, orderDto: OrderDto): Observable<any> {
    return this.http.post(`${BASE_URL}/${customerId}/projects/${projectId}/orders`, orderDto, {
      headers: this.createAuthorizationHeader()
    });
  }

  
  placeOffer(customerId: number, projectId: number, offerDto: OfferDto): Observable<any> {
    return this.http.post(`${BASE_URL}/${customerId}/projects/${projectId}/offers`, offerDto, {
      headers: this.createAuthorizationHeader()
    });
  }

 
  getCustomerOrders(customerId: number): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/${customerId}/orders`, {
      headers: this.createAuthorizationHeader()
    });
  }
  getCustomerOffers(customerId: number): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/${customerId}/offers`, {
      headers: this.createAuthorizationHeader()
    });
  }
  getRejectedOrders(customerId: number): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/${customerId}/orders/rejected`, {
      headers: this.createAuthorizationHeader()
    });
  }
  getRejectedOffers(customerId: number): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/${customerId}/offers/rejected`, {
      headers: this.createAuthorizationHeader()
    });
  }

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization','Bearer '+StorageService.getToken()
    )
  }
}
