import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { finalize, Observable } from 'rxjs';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private appServerUrl = environment.appBaseUrl;
  
  
  constructor( private http: HttpClient,private router :Router) { }

  public signUp(signUpRequest: any):Observable<any>{
    return this.http.post<any>(`${this.appServerUrl}/api/auth/signup`,signUpRequest);
  }

  login(loginRequest: any):Observable<any>{
    return this.http.post(`${this.appServerUrl}/api/auth/login`,loginRequest)
    .pipe(finalize(()=>{
      if(StorageService.isAdminUserLoggedIn()){
        this.router.navigate(['/admin/dashboard']);
      }else if(StorageService.isUserLoggedIn()){
        this.router.navigate(['/customer/dashboard']);
      }else{
        alert("BAD Credentials");
      }
    }));
  }
  logout(token: string){
    return this.http.get(`${this.appServerUrl}/api/auth/logout?token=${token}`);
  }
}
