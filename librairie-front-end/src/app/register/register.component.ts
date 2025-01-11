import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder) {
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

  register() {
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
