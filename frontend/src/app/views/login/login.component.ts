import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth.service'; // Asegúrate de que el path sea correcto
import { AuthResponse } from '../../model/auth-response.model'; // Asegúrate de que el path sea correcto

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  submitted = false;
  loading = false;
  showError = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      rememberMe: [false]
    });
  }

  get f() { return this.loginForm.controls; }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    const credentials = this.loginForm.value;

    this.authService.login(credentials).subscribe(
      (response: AuthResponse) => {
        console.log('Login successful', response);
        this.loading = false;
        this.router.navigate(['/home']); // Redirige a la vista deseada
      },
      (error: any) => {
        console.error('Login failed', error);
        this.loading = false;
        this.showError = true;
      }
    );
  }

  dismissError() {
    this.showError = false;
  }
}
