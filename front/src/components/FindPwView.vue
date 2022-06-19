<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>&nbsp;</h6>
              <h2>비밀번호 찾기</h2>
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
                  <center>입력된 번호로 비밀번호가 전송 됩니다.</center>
                  <div class="space-y-6 text-center mt-8 md:mt-14 mb-6"></div>

                  <hr />
                  <form role="form">
                    <div class="form-group">
                      <label for="userEmail"
                        ><span class="glyphicon glyphicon-user"></span>email</label
                      >
                      <input
                        type="text"
                        v-model="userEmail"
                        class="form-control"
                        :disabled="disabled == 1"
                        placeholder="가입시 등록한 이메일을 입력하세요."
                        required
                      />
                    </div>
                    <div class="form-group">
                      <label for="userName"
                        ><span class="glyphicon glyphicon-eye-open"></span> name</label
                      >
                      <input
                        type="name"
                        class="form-control"
                        v-model="userName"
                        placeholder="가입시 등록한 이름을 입력하세요."
                        :disabled="disabled == 1"
                        required
                      />
                    </div>
                    <button type="button" class="btn btn-secondary btn-block" @click="validate">
                      찾기
                    </button>
                  </form>
                  <hr />
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
  name: "FindPwView",
  data() {
    return {
      userId: "",
      userName: "",
      userPw: "",
      userEmail: "",
      ok: "",
      disabled: 0,
    };
  },
  methods: {
    validate() {
      this.ok = "";
      let err = true;
      let msg = "";
      !this.userEmail && ((msg = "Email를 입력해주세요!"), (err = false));
      err && !this.userName && ((msg = "이름을 입력해주세요!"), (err = false));
      err && !this.userEmail && ((msg = "이메일을 입력해주세요!"), (err = false));
      if (!err) alert(msg);
      else this.findPw();
    },

    async findPw() {
      let findObj = {
        userName: this.userName,
        userEmail: this.userEmail,
      };

      let { data } = await http.post("/mail", findObj);
      console.log(data);

      this.$alertify.alert("입력하신 이메일로 비밀번호가 발송되었습니다.");
    },
  },
};
</script>

<style scoped>
.findpw {
  background-color: white;
  margin-left: 20px;
}
.answer {
  color: white;
}
#btn {
  border: solid 1px #01579b;
}
.button {
  margin: 0 auto;
}
.btnFind {
  background-color: white;
}
</style>
