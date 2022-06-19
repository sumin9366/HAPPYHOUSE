<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>&nbsp;</h6>
              <h2>login</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="contact-page">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-6">
            <div class="inner-content">
              <div class="row justify-content-center">
                <div class="align-self-center col-lg-11 my-margin">
                  <form id="contact">
                    <div class="wrapper">
                      <form class="form-signin">
                        <h2 class="form-signin-heading">Please login</h2>
                        <input
                          type="text"
                          v-model="userId"
                          class="form-control"
                          placeholder="Id"
                          required=""
                          autofocus=""
                        />
                        <input
                          type="password"
                          class="form-control"
                          v-model="userPw"
                          placeholder="Password"
                          required=""
                        />

                        <button
                          class="btn btn-lg btn-primary btn-block"
                          type="button"
                          @click="login"
                          style="margin-bottom: 2px"
                        >
                          Login
                        </button>
                        <button
                          class="btn btn-lg btn-primary btn-block"
                          type="submit"
                          @click="register"
                          style="margin-bottom: 10px"
                        >
                          회원가입
                        </button>

                        <router-link
                          type="button"
                          to="/findpw"
                          class="button outlined-brand-1 font-normal"
                          style="margin-left: 370px"
                        >
                          비밀번호찾기
                        </router-link>
                      </form>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

import http from "@/common/axios.js";
export default {
  name: "LoginView",

  data() {
    return {
      userId: null,
      userPw: null,
      userAddress: null,
    };
  },

  methods: {
    async login() {
      let loginObj = {
        userId: this.userId,
        userPw: this.userPw,
      };
      console.log(loginObj);

      try {
        let { data } = await http.post("/login", loginObj);
        console.log("LoginVue: data: ");
        console.log(data);

        this.$store.commit("loginStore/SET_LOGIN", {
          isLogin: true,
          userName: data.user.userName,
          userId: data.user.userId,
          userAddress: data.user.userAddress,
          userEmail: data.user.userEmail,
          userPhone: data.user.userPhone,
          userClsf: data.user.userClsf,
        });

        this.$router.push("/");
      } catch (error) {
        console.log("LoginVue: error : ");
        console.log(error);
        if (error.response.status == "404") {
          this.$alertify.error("이메일 또는 비밀번호를 확인하세요.");
        } else {
          this.$alertify.error("Opps!! 서버에 문제가 발생했습니다.");
        }
      }
    },
    register() {
      this.$router.push("/register");
    },
  },
};
</script>

<style scoped>
.page-heading {
  padding: 90px 0px 30px 80px;
  margin: 0px !important;
}
.my-margin {
  margin: 70px 0px 80px 0px !important;
}
.contact-page {
  margin: 50px !important;
}
</style>
