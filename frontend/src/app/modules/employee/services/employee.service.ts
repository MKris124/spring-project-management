import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { StorageService } from '../../../auth/services/storage/storage.service';
import { Observable } from 'rxjs';

const BASE_URL = "http://localhost:8080/api/employee";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http:HttpClient) { }


  getAssignedTasks(userId: number): Observable<any> {
    return this.http.get<any>(
      `${BASE_URL}/${userId}/tasks`,
      { headers: this.createAuthorizationHeader() }
    );
  }

  updateTaskStatus(taskId: number, newStatus: string): Observable<any> {
    return this.http.put(
      `${BASE_URL}/tasks/${taskId}/status`,
      null,
      {
        params: { newStatus },
        headers: this.createAuthorizationHeader(),
      }
    );
  }

  private createAuthorizationHeader():HttpHeaders{
    return new HttpHeaders().set(
      'Authorization','Bearer '+StorageService.getToken()
    )
  }
}
