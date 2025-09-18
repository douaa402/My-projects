<template>
  <form action="" @submit.prevent="login">
            <div class="row g-3 align-items-center">
                    <h1>Login</h1>
                    <div class="col-auto d-block mx-auto">
                        <input type="email" name="" id="" class="form-control" placeholder="Enter your Email" v-model="state.email">
                        <span class="error-feedback" v-if="v$.email.$error">{{v$.email.$errors[0].$message}}</span>
                    </div>
            </div>
            <br>
            <div class="row g-3 align-items-center">
                    <div class="col-auto d-block mx-auto">
                        <input type="password" name="" id="" class="form-control" placeholder="Enter your password" v-model="state.pass">
                        <span class="error-feedback" v-if="v$.pass.$error">{{v$.pass.$errors[0].$message}}</span>
                    </div>
            </div>
            <br>
            <div class="row g-3 align-items-center">
                    <div class="col-auto d-block mx-auto">
                    <button type="submit" class="btn btn-primary" @click="loginUser()">Login</button> &nbsp;&nbsp;&nbsp;
                    <button type="submit" class="btn btn-link" @click="redirectTo({ val: 'signUp'})">sign Up</button>
                    </div>
            </div>
            <br>
            <div class="row g-3 align-items-center">
                <div class="col-auto d-block mx-auto error-feedback ">
                    {{ userNotFoundErr }}
                </div>
                   
            </div>
  </form>
</template>

<script>
import axios from "axios" ;
import { mapActions } from 'vuex';
import useValidate from "@vuelidate/core";
import { required, email , minLength } from "@vuelidate/validators";
import { reactive, computed } from "vue";
export default {
    name: "loginForm",
    //composition API
    setup(){
        //data
        const state = reactive({
            pass: "",
            email: "",
        });
        //validations
        const rules = computed( ()=> {
            return{
                email: { required, email},
                pass : { required, minLength: minLength(10) },
            };
        });
        const v$ = useValidate(rules, state);
        return {
            state, v$
        }
    },
    data(){
        return{
            userNotFoundErr:"",

        };
    },
    mounted(){
        let user = localStorage.getItem('user-info');
        if (user) {
            this.redirectTo({ val: 'home' });
            }
    },
    methods: {
        ...mapActions(['redirectTo']),
       /*  signUpPage(){
            this.$router.push({name: "signUp"});
        } */
       async loginUser(){
         this.v$.$validate();
            if(!this.v$.$error){
                console.log("form valider");
                let result = await axios.get(`http://localhost:3000/users?email=${this.state.email}&pass=${this.state.pass}`);
                if(result.status == 200 && result.data.length > 0){
                    localStorage.setItem("user-info", JSON.stringify(result.data[0]));
                    this.redirectTo({ val: 'home' });
                }else{
                    this.userNotFoundErr="User not Found";
                }
                //console.log(result);
            }else{
                console.log("unvalide");
            }
       },
    },
};
</script>

<style scoped>
.error-feedback{
    color: red;
    font-size: 0.85em;
}
</style>
