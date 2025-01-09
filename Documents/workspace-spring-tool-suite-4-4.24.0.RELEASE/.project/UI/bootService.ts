import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BootServiceService {

  private baseUrl = 'http://localhost:8080/ftlCheck';

  constructor(private http: HttpClient) {}

  createGenerateJsonToJson(jsonContent: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/create`, jsonContent);
  }  

  listGenerateJsonToJson(jsonContent: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/list`, jsonContent);
  }  

  detailGenerateJsonToJson(jsonContent: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/detail`, jsonContent);
  }  
}
