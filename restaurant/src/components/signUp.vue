<template>
  <form action="" @submit.prevent>
            <div class="row g-3 align-items-center">
                <h1>Sign Up</h1>
                <div class="col-auto d-block mx-auto">
                    <input type="text" name="" id="" class="form-control" placeholder="Enter your Name" v-model="name">
                    <span class="error-feedback" v-if="v$.name.$error">{{v$.name.$errors[0].$message}}</span>
                </div>
            </div>
            <br>
            <div class="row g-3 align-items-center">
                    <div class="col-auto d-block mx-auto">
                        <input type="email" name="" id="" class="form-control" placeholder="Enter your Email" v-model="email">
                        <span class="error-feedback" v-if="v$.email.$error">{{v$.email.$errors[0].$message}}</span>
                    </div>
            </div>
            <br>
            <div class="row g-3 align-items-center">
                    <div class="col-auto d-block mx-auto">
                        <input type="password" name="" id="" class="form-control" placeholder="Enter your password" v-model="pass">
                        <span class="error-feedback" v-if="v$.pass.$error">{{v$.pass.$errors[0].$message}}</span>
                    </div>
            </div>
            <br>
            <div class="row g-3 align-items-center">
                    <div class="col-auto d-block mx-auto">
                    <button type="submit" class="btn btn-primary" @click="signUp()">sign up</button>&nbsp;&nbsp;&nbsp;
                    <button type="submit" class="btn btn-link" @click="redirectTo({ val: 'login'})">login</button>
                    </div>
            </div>
  </form>
</template>


<script>
import axios from "axios";
import { mapActions } from 'vuex';
import useVuelidate from "@vuelidate/core";
import { required, email, minLength } from "@vuelidate/validators"; //pour validation form

export default {
    name: "signUpForm",
    data(){
        return{
            v$: useVuelidate(),
            name: '',
            pass: '',
            email: '',

        };
    },
    validations(){
        return{
            name: { required },
            pass: { required, minLength: minLength(10) },
            email: { required, email },
        };
    },
    
    methods: {
        ...mapActions(['redirectTo']),
        /* loginPage(){
            this.$router.push({name: "login"});
        } */
       async signUp(){
            this.v$.$validate();
            if(!this.v$.$error){
                console.log("form valider");
                let result = await axios.post('http://localhost:3000/users', {
                    name: this.name,
                    email: this.email,
                    pass: this.pass,
                });
                if( result.status == 201){
                        console.log("addition valide");
                        //save user data in local storage
                        localStorage.setItem("user-info", JSON.stringify(result.data));
                        
                        //redirect home page
                        this.redirectTo({val: 'home'})
                        
                }else{
                    console.log("addition no valide");

                }
            }else{
                console.log("unvalide");
            }
       },
    }
};
</script>

<style scoped>
.error-feedback{
    color: red;
    font-size: 0.85em;
}
</style>

