import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { StorageService } from '../../../auth/services/storage/storage.service';
import { TaskDto } from '../../../models/task.dto';
import { ProjectDto } from '../../../models/project.dto';

const BASE_URL = "http://localhost:8080/api/manager";

@Injectable({
  providedIn: 'root'
})
export class ManagerService {

  constructor(private http:HttpClient) { }

  
  getProjects(managerId: string): Observable<any> {
    var managerID:number = +managerId;
    return this.http.get(`${BASE_URL}/${managerID}/projects`, {
      headers: this.createAuthorizationHeader()
    });
  }

  getTaskById(projectId:number):Observable<any> {
    return this.http.get(`${BASE_URL}/projects/${projectId}/tasks`, {
      headers: this.createAuthorizationHeader()
    });
  }
 
  createProject(managerId: number, project: ProjectDto): Observable<any> {
    return this.http.post(`${BASE_URL}/${managerId}/projects`, project, {
      headers: this.createAuthorizationHeader()
    });
  }

 
  createTask(projectId: number, task: TaskDto): Observable<any> {
    return this.http.post(`${BASE_URL}/projects/${projectId}/tasks`, task, {
      headers: this.createAuthorizationHeader()
    });
  }

 
  updateTaskStatus(taskId: number, newStatus: string): Observable<any> {
    return this.http.put(`${BASE_URL}/tasks/${taskId}/status`, {}, {
      params: { newStatus },
      headers: this.createAuthorizationHeader()
    });
  }

  
  removeProject(projectId: number): Observable<any> {
    return this.http.delete(`${BASE_URL}/projects/${projectId}`, {
      headers: this.createAuthorizationHeader()
    });
  }

  
  updateProjectPhase(projectId: number, newPhase: string): Observable<any> {
    return this.http.put(`${BASE_URL}/projects/${projectId}/phase`, {}, {
      params: { newPhase },
      headers: this.createAuthorizationHeader()
    });
  }

  getAllEmployees(): Observable<any> {
    return this.http.get(`${BASE_URL}/employees`, {
      headers: this.createAuthorizationHeader()
    });
  }

  acceptOrder(orderId: number, managerId: number): Observable<any> {
    return this.http.put(`${BASE_URL}/orders/${orderId}/accept`, {}, {
      params: { managerId },
      headers: this.createAuthorizationHeader()
    });
  }

  rejectOrder(orderId: number): Observable<any> {
    return this.http.put(`${BASE_URL}/orders/${orderId}/reject`, {}, {
      headers: this.createAuthorizationHeader()
    });
  }
  acceptOffer(offerId: number, managerId: number): Observable<any> {
    return this.http.put(`${BASE_URL}/offers/${offerId}/accept`, {}, {
      params: { managerId },
      headers: this.createAuthorizationHeader()
    });
  }

  rejectOffer(offerId: number): Observable<any> {
    return this.http.put(`${BASE_URL}/offers/${offerId}/reject`, {}, {
      headers: this.createAuthorizationHeader()
    });
  }

  getPendingOffers(): Observable<any> {
    return this.http.get(`${BASE_URL}/offers/pending`, {
      headers: this.createAuthorizationHeader()
    });
  }

  getPendingOrders(): Observable<any> {
    return this.http.get(`${BASE_URL}/orders/pending`, {
      headers: this.createAuthorizationHeader()
    });
  }
  

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization','Bearer '+StorageService.getToken()
    )
  }
}
