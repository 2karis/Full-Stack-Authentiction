import { Injectable } from '@angular/core';

const TOKEN = "token";
const USER = "user";
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  static saveToken(token:string):void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN,token)
  }
  static saveUser(user:any):void{
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER,JSON.stringify(user))
  }
  static getToken():any{
    return window.localStorage.getItem(TOKEN);
  }
  static getUser():any{
    return window.localStorage.getItem(USER);
  }


  static getUserRole(){
    const user = this.getUser();
    if(user== null){
      return "";
    }
    return user.role;
  }

  static isUserLoggedIn(){
    if(this.getToken()==null){
      return false;
    }
    return true;
    // const role:string = this.getUserRole();
    // console.log("token saved is  "+this.getToken())

    // return role == "USER";
  }

  static isSuperUserLoggedIn(){
    if(this.getToken()==null){
      return false;
    }
    const role:string = this.getUserRole();
    return role == "SUPER_USER";
  }

  static isAdminUserLoggedIn(){
    if(this.getToken()==null){
      return false;
    }
    const role:string = this.getUserRole();
    return role == "ADMIN_USER";
  }
  static logout():void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(USER);
  }
}
