import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators , AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
 loginForm: FormGroup;
 constructor(private fb: FormBuilder) {
  this.loginForm = this.fb.group({
    
    email: ['', [Validators.required, Validators.email,
      this.emailWithDotValidator,
    
    ]],
    password: [
      '',
      [
        Validators.required,
        
        
          
        
      ],
    ],
    
  });
}

  login(){
    if (this.loginForm.valid) {
      console.log('Form Submitted', this.loginForm.value);
    } else {
      this.loginForm.markAllAsTouched();
    }
  }
  get email() {
    return this.loginForm.get('email');
  }
  get password() {
    return this.loginForm.get('password');
  }
  emailWithDotValidator(control: AbstractControl): { [key: string]: any } | null {
    const value = control.value;
    if (value && !value.includes('.')) {
      return { missingDot: true }; // Retourne une erreur si le point est absent
    }
    return null; // Aucune erreur
  }
}
