<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>&nbsp;</h6>
              <h2>register</h2>
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
                  <div class="space-y-6 text-center mt-8 md:mt-14 mb-6">
                    <h2 class="h2 undefined">회원가입</h2>
                    <p class="text-12 md:text-14 text-gray-600 leading-normal">
                      회원가입을 환영합니다.<br />회원으로 가입하시면 HAPPY HOUSE 에서 운영하는<br />
                      아파트 주택 거래 정보 서비스를 이용하실 수 있습니다.<br />
                    </p>
                  </div>
                  <form id="contact">
                    <div class="row">
                      <div class="">
                        <fieldset>
                          <label class="label">아이디</label>
                          <input
                            type="id"
                            :class="{
                              'is-valid': isUserIdFocusAndValid,
                              'is-invalid': isUserIdFocusAndInvalid,
                            }"
                            v-model="userId"
                            placeholder="ID"
                            @input="validateUserId"
                            @focus="isUserIdFocus = true"
                            autocomplete="on"
                            required
                          />
                        </fieldset>
                      </div>
                      <div class="col-lg-12">
                        <fieldset>
                          <label class="label">비밀번호</label>
                          <input
                            type="password"
                            v-model="userPw"
                            @input="validatePw"
                            @focus="isUserPwFocus = true"
                            placeholder="password"
                            required=""
                          />
                          <div class="valid-feedback">Valid.</div>
                          <!-- 약한 조건 -->
                          <div class="invalid-feedback">4글자 이상을 입력하세요.</div>
                        </fieldset>
                      </div>
                      <div class="col-lg-12">
                        <fieldset>
                          <label class="label">이름</label>
                          <input
                            type="name"
                            :class="{
                              'is-valid': isUserNameFocusAndValid,
                              'is-invalid': isUserNameFocusAndInvalid,
                            }"
                            v-model="userName"
                            @input="validateUserName"
                            @focus="isUserNameFocus = true"
                            placeholder="name"
                            autocomplete="on"
                            required
                          />
                        </fieldset>
                      </div>
                      <div class="col-lg-12">
                        <fieldset>
                          <label class="label">이메일</label>
                          <input
                            type="email"
                            :class="{
                              'is-valid': isUserEmailFocusAndValid,
                              'is-invalid': isUserEmailFocusAndInvalid,
                            }"
                            v-model="userEmail"
                            @input="validateEmail"
                            @focus="isUserEmailFocus = true"
                            placeholder="Your Email"
                            required=""
                          />
                        </fieldset>
                      </div>

                      <label class="label">핸드폰번호</label>
                      <div class="grid grid-cols-3 gap-4">
                        <fieldset class="input-phone input-phone-3">
                          <input
                            type="text"
                            v-model="phone1"
                            class="input0"
                            maxLength="3"
                            placeholder="010"
                          />
                        </fieldset>
                        -
                        <fieldset class="input-phone input-phone-4">
                          <input
                            type="text"
                            v-model="phone2"
                            class="input0"
                            maxLength="4"
                            placeholder="0000"
                          />
                        </fieldset>
                        -
                        <fieldset class="input-phone input-phone-4">
                          <input
                            type="text"
                            v-model="phone3"
                            class="input0"
                            maxLength="4"
                            placeholder="0000"
                          />
                        </fieldset>
                      </div>

                      <label class="label">주소</label>
                      <div class="col-lg-12">
                        <input type="text" v-model="postcode" placeholder="우편번호" readonly />
                      </div>
                      <div class="col-lg-12">
                        <button
                          type="button"
                          @click="execDaumPostcode()"
                          style="background-color: #8d99af; margin-bottom: 25px"
                        >
                          <i class="fa fa-paper-plane"></i> 우편번호 찾기
                        </button>
                      </div>
                      <div class="col-lg-12">
                        <input type="text" v-model="address" placeholder="주소" />
                      </div>
                      <div class="col-lg-12">
                        <input type="text" id="detailAddress" placeholder="상세주소" />
                      </div>
                      <div class="col-lg-12">
                        <input type="text" v-model="extraAddress" placeholder="참고항목" />
                      </div>

                      <div class="col-lg-12">
                        <fieldset>
                          <button
                            class="btn btn-lg btn-primary btn-block"
                            type="submit"
                            @click="register"
                          >
                            가입하기
                          </button>
                        </fieldset>
                      </div>
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
  data() {
    return {
      userId: "",
      userPw: "",
      userName: "",
      userEmail: "",

      userAptCode: "",
      postcode: "",
      address: "",
      extraAddress: "",
      phone1: "",
      phone2: "",
      phone3: "",
      userPhone: "",

      // focus
      isUserIdFocus: false,
      isUserPwFocus: false,
      isUserNameFocus: false,
      isUserEmailFocus: false,
      // isUserPhoneFocus: false,

      // validation
      isUserIdValid: false,
      isUserPwValid: false,
      isUserNameValid: false,
      isUserEmailValid: false,
      // isUserPhoneValid: false,

      // 회원 구분
      groupCode: "001",
      codeList: [],
      userClsf: "001", // 일반회원 default
    };
  },
  computed: {
    isUserIdFocusAndValid() {
      return this.isUserIdFocus && this.isUserIdValid;
    },
    isUserIdFocusAndInvalid() {
      return this.isUserIdFocus && !this.isUserIdValid;
    },
    isUserPwFocusAndValid() {
      return this.isUserPwFocus && this.isUserPwValid;
    },
    isUserPwFocusAndInValid() {
      return this.isUserPwFocus && !this.isUserPwValid;
    },
    isUserNameFocusAndValid() {
      return this.isUserNameFocus && this.isUserNameValid;
    },
    isUserNameFocusAndInvalid() {
      return this.isUserNameFocus && !this.isUserNameValid;
    },
    isUserEmailFocusAndValid() {
      return this.isUserEmailFocus && this.isUserEmailValid;
    },
    isUserEmailFocusAndInvalid() {
      return this.isUserEmailFocus && !this.isUserEmailValid;
    },
    // isUserPhoneFocusAndValid() {
    //   return this.isUserEmailFocus && this.isUserEmailValid;
    // },
    // isUserPhoneFocusAndInvalid() {
    //   return this.isUserPhoneFocus && !this.isUserPhoneValid;
    // },
  },
  methods: {
    validateUserId() {
      this.isUserIdValid = this.userId.length > 0 ? true : false;
      console.log(this.isUserIdValid);
    },
    validateUserName() {
      this.isUserNameValid = this.userName.length > 0 ? true : false;
      console.log(this.isUserNameValid);
    },
    validateEmail() {
      // ^ 시작일치, $ 끝 일치
      // {2, 3} 2개 ~ 3개
      // * 0회 이상, + 1회 이상
      // [-_.] - 또는 _ 또는 .
      // ? 없거나 1회
      let regexp =
        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      this.isUserEmailValid = regexp.test(this.userEmail) ? true : false;
      console.log(this.isUserEmailValid);
    },
    validatePw() {
      let patternEngAtListOne = new RegExp(/[a-zA-Z]+/); // + for at least one
      let patternSpeAtListOne = new RegExp(/[~!@#$%^&*()_+|<>?:{}]+/); // + for at least one
      let patternNumAtListOne = new RegExp(/[0-9]+/); // + for at least one

      this.isUserPwValid =
        patternEngAtListOne.test(this.userPw) &&
        patternSpeAtListOne.test(this.userPw) &&
        patternNumAtListOne.test(this.userPw) &&
        this.userPw.length >= 8
          ? true
          : false;
    },

    execDaumPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          if (this.extraAddress !== "") {
            this.extraAddress = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            this.address = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.address = data.jibunAddress;
          }

          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if (data.userSelectedType === "R") {
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extraAddress += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extraAddress +=
                this.extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (this.extraAddress !== "") {
              this.extraAddress = `(${this.extraAddress})`;
            }
          } else {
            this.extraAddress = "";
          }
          // 우편번호를 입력한다.
          this.postcode = data.zonecode;
          this.address = data.address;
        },
      }).open();
    },
    async register() {
      if (!this.isUserPwValid) {
        return this.$alertify.error(
          "비밀번호는 8자리 이상 대소문자, 특수문자, 숫자를 조합하셔야 합니다 "
        );
      }
      if (!this.isUserEmailValid) {
        return this.$alertify.error("이메일이 유효한지 확인하세요.");
      }

      let registerObj = {
        userId: this.userId,
        userName: this.userName,
        userEmail: this.userEmail,
        userPhone: this.phone1.concat(this.phone2, this.phone3),
        userPw: this.userPw,
        userAddress: this.address,
        userClsf: this.userClsf,
      };

      console.log(registerObj);
      //console.log(1);
      try {
        // console.log("222222222222222");
        let { data } = await http.post("/users", registerObj); // JSON Request, { params : registerObj } X params 를 쓰면 get => query string
        console.log("RegisterVue: data : ");

        console.log(data);
        //console.log("3333333333333333333");
        // setTimeout(() => console.log(data.result), 5000);
        //setTimeout(() => console.log(data.result), 5000);

        if (data.result == "success") {
          let $this = this;
          this.$alertify.alert("회원가입을 축하합니다. 로그인 페이지로 이동합니다", function () {
            $this.$router.push("/login");
          });
        } else {
          console.log("RegisterVue: error : ");
          this.$alertify.error("서버에 문제가 발생했습니다.");
        }
      } catch (error) {
        console.log("RegisterVue: error : ");
        console.log(error);
        this.$alertify.error("서버에 문제가 발생했습니다.");
      }
      //console.log(4);
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
.input-phone {
  display: inline-block !important;
}
.input-phone-3 {
  width: 23%;
}
.input-phone-4 {
  width: 28%;
}
</style>
