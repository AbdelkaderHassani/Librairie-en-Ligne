import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth-service.service';
import { User } from '../models/user';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  user: User = new User;
  registerForm: FormGroup;
  registeruser = { email: '', password: '', username: '' };

  message: string = '';
  constructor(private fb: FormBuilder,private authService: AuthService) {
    this.registerForm = this.fb.group({
      fname: ['', [Validators.required, Validators.minLength(3)]],
      lname: ['', [Validators.required, Validators.minLength(3)]],
      address: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      confirmationemail: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/
          ),
        ],
      ],
      repeatepassword: ['', [Validators.required]],
      gender: ['', Validators.required],
      dob: ['', Validators.required],
    });
  }

  signup() {
    this.authService.signup(this.registeruser).subscribe(
      response => {
        this.message = 'Inscription réussie !';
      },
      error => {
        console.error(error);
        this.message = 'Erreur lors de l’inscription.';
      }
    );
  }


  signupVal() {
    if (this.registerForm.valid) {
      console.log('Form Submitted', this.registerForm.value);
    } else {
      this.registerForm.markAllAsTouched();
    }
    
  }

  get fname() {
    return this.registerForm.get('fname');
  }
  get lname() {
    return this.registerForm.get('lname');
  }
  get address() {
    return this.registerForm.get('address'); // Getter pour l'accès au contrôle
  }
  get email() {
    return this.registerForm.get('email');
  }
  get confirmationemail(){
    return this.registerForm.get('confirmationemail');
  }
  get password() {
    return this.registerForm.get('password');
  }
  get repeatepassword() {
    return this.registerForm.get('repeatepassword');
  }
  get dob() {
    return this.registerForm.get('dob');
  }
  get gender(){
    return this.registerForm.get('gender');
  }
}
