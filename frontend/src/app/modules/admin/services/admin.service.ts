import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../../../auth/services/storage/storage.service';

const BASE_URL = "http://localhost:8080/api";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  
  getEmployees(): Observable<any> {
    return this.http.get(BASE_URL+"/admin/employees",{
      headers:this.createAuthorizationHeader()
    })
  }

  
  getCustomers(): Observable<any> {
    return this.http.get(`${BASE_URL}/admin/customers`,{
      headers:this.createAuthorizationHeader()
    })
  }

  
  deleteUser(userId: number): Observable<any> {
    return this.http.delete(`${BASE_URL}/admin/users/${userId}`,{
      headers:this.createAuthorizationHeader()
    })
  }

  
  updateUserRole(userId: number, newRole: string): Observable<any> {
    let params= new HttpParams().set("newRole", newRole);
    return this.http.put(`${BASE_URL}/admin/users/${userId}/role`, { },{
      headers:this.createAuthorizationHeader(),
      params:params
    })
  }

  
  resetPassword(userId: number, newPassword: string): Observable<any> {
    return this.http.put(`${BASE_URL}/admin/users/${userId}/reset-password`, { newPassword },{
      headers:this.createAuthorizationHeader()
    })
  }
  
  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization','Bearer '+StorageService.getToken()
    )
  }
}
