import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  forgotPasswordForm: FormGroup;

  constructor(private fb: FormBuilder) {
    // Initialisation du formulaire réactif avec les validations
    this.forgotPasswordForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // Champ email avec validations
    });
  }

  // Getter pour accéder facilement au champ 'email'
  get email() {
    return this.forgotPasswordForm.get('email');
  }

  // Méthode appelée lorsque l'utilisateur clique sur "Reset Password"
  onResetPassword() {
    if (this.forgotPasswordForm.valid) {
      const emailValue = this.forgotPasswordForm.value.email;
      console.log('Password reset link sent to:', emailValue);
      // Ajoutez ici la logique pour envoyer l'email de réinitialisation de mot de passe
    } else {
      this.forgotPasswordForm.markAllAsTouched(); // Marque tous les champs comme "touchés" pour afficher les erreurs
    }
  }
}
