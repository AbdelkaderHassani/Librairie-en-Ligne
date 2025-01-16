import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators , AbstractControl } from '@angular/forms';
import { AuthService } from '../service/auth-service.service';
import { User } from '../models/user';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  user: User = new User;
  registerForm: FormGroup;
  registeruser = { fname: '', lname: '', address: '', email: '', password: '', repeatepassword: '', gender: '', dob: '' };

  message: string = '';
  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      fname: ['', [Validators.required, Validators.minLength(3)]],
      lname: ['', [Validators.required, Validators.minLength(3)]],
      address: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email, this.emailWithDotValidator]],
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
      repeatepassword: ['', [Validators.required,
                             Validators.minLength(8),
                             Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]+$/)
      ]],
      gender: ['', Validators.required],
      dob: ['', Validators.required],
    }, { validators: this.passwordMatchValidator });
  }
  
  // Méthode de validation personnalisée pour vérifier que les mots de passe sont identiques
  passwordMatchValidator(group: FormGroup): { [key: string]: boolean } | null {
    const password = group.get('password')?.value;
    const repeatepassword = group.get('repeatepassword')?.value;
    if (password && repeatepassword && password !== repeatepassword) {
      return { mismatch: true };
    }
    return null;
  }
  
  
  // Validation personnalisée pour vérifier la présence d'un point
  emailWithDotValidator(control: AbstractControl): { [key: string]: any } | null {
    const value = control.value;
    if (value && !value.includes('.')) {
      return { missingDot: true }; // Retourne une erreur si le point est absent
    }
    return null; // Aucune erreur
  }
  

  signup() {
    console.log('Form Submitted:', this.registerForm.value);  // Vérifiez les données soumises
    this.registeruser.lname = this.registerForm.value.lname;
    this.registeruser.fname = this.registerForm.value.fname;
    this.registeruser.address = this.registerForm.value.address;
    this.registeruser.email = this.registerForm.value.email;
    this.registeruser.password = this.registerForm.value.password;
    this.registeruser.repeatepassword = this.registerForm.value.repeatepassword;
    this.registeruser.gender = this.registerForm.value.gender;
    this.registeruser.dob = this.registerForm.value.dob;
    
    this.authService.signup(this.registeruser).subscribe(
      response => {
        this.message = 'Inscription réussie !';
        this.router.navigate(['/login']);
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
