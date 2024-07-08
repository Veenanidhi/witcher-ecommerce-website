  document.addEventListener('DOMContentLoaded', function() {
  const form = document.getElementById('form-id');

  const username = document.getElementById('username');
  const email = document.getElementById('email');
  const password = document.getElementById('password');
  const confirmPassword = document.getElementById('confirmPassword');
  const number = document.getElementById('number');

  const usernameError = document.getElementById('usernameError');
  const emailError = document.getElementById('emailError');
  const passwordError = document.getElementById('passwordError');
  const confirmPasswordError = document.getElementById('confirmPasswordError');
  const numberError = document.getElementById('numberError');

  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  const passwordPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  function validateForm() {
      const error = [];
      if (!username.value) {
          usernameError.textContent = "Username is required.";
          error.push("username");
      } else {
          usernameError.textContent = "";
      }
      if (!emailPattern.test(email.value)) {
          emailError.textContent = "Invalid email format.";
          error.push("email");
      } else {
          emailError.textContent = "";
      }
      if (!passwordPattern.test(password.value)) {
          passwordError.textContent = "Password must be at least 8 characters long and include an uppercase letter, a number, and a special character.";
          error.push("password");
      } else {
          passwordError.textContent = "";
      }
      if (password.value !== confirmPassword.value) {
          confirmPasswordError.textContent = "Passwords do not match.";
          error.push("confirmPassword");
      } else {
          confirmPasswordError.textContent = "";
      }
      if (number.value.length > 0 &&  number.value.length!==10) {
          numberError.textContent = "Number is invalid.";
          error.push("number");
      } else {
          numberError.textContent = "";
      }

      if (error.length > 0) {
          return false;
      }

      return true;
  }

  form.addEventListener('submit', function(event) {
      console.log("Submitting form");
      if (!validateForm()) {
          console.log("FORM NOT VALIDATED")
          event.preventDefault();
      }
  });
});








