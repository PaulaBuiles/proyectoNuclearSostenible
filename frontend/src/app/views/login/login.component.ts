import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service'; // Asegúrate de que el path sea correcto
import { AuthResponse } from '../../model/auth-response.model'; // Asegúrate de que el path sea correcto

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      userName: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {

    if (this.loginForm.invalid) {
      return;
    }


    const credentials = this.loginForm.value;


    this.authService.login(credentials).subscribe(
      (response: AuthResponse) => {
        this.router.navigate(['/products']); // Redirige a la vista deseada
      },
      (error: any) => {
        console.error('Login failed', error);
      }
    );
  }

  dismissError() {
  }
}
