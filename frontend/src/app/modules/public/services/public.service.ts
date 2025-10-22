import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PublicService {

  private readonly BASE_URL = 'http://localhost:8080/api/public/projects';

  constructor(private http: HttpClient) {}

  getRunningProjects(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/running`);
  }
}
