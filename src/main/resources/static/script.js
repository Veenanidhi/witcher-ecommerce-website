const username = document.getElementById('username').value;
const email = document.getElementById('email').value;
const password = document.getElementById('password').value;
const confirmPassword = document.getElementById('confirmpassword').value;
const number = document.getElementById('number').value;
const submitButton = document.getElementById('submit');
const error = []
const emailPattern = "^(?=.[A-Z])(?=.\d)(?=.[@$!%?&])[A-Za-z\d@$!%*?&]{8,}$";
const passwordPattern = "^(?=.[A-Z])(?=.\d)(?=.[@$!%?&])[A-Za-z\d@$!%*?&]{8,}$";
const form=document.getElementById('form_id');

submitButton.addEventListener("click",(e)=>{
     e.preventDefault();

 if(username.trim(" ") === ""){
  username.textContent="username is invalid";
  error.push("message")
 }
 
   if(emailPattern.test(email)){
 
   email.textContent="email not valid";
   error.push("message")
 }

 if(passwordPattern.test(password)){
  password.textContent="password invalid";
  error.push("message")
  
 }

 if(confirmPassword!= password) {
  confirmPassword.textContent="Passwords do not match";
  error.push("message")
} 

if(number.length!=10){
  number.textContent("10 digits required");
  error.push("message");
}

 
if(errormessages.length===0){
  form.submit(valid);
}
   
});








