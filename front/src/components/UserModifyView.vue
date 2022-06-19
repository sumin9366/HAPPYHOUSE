<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>&nbsp;</h6>
              <h2>Modify</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="contact-page m-1">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-6">
            <div class="inner-content">
              <div class="row justify-content-center">
                <div class="align-self-center col-lg-11 my-margin">
                  <div class="space-y-6 text-center mt-8 md:mt-14 mb-6">
                    <h2 class="h2 undefined">회원정보수정</h2>
                  </div>
                  <form id="contact">
                    <div class="row">
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
                            v-model="userName"
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
                      <!-- <div class="col-lg-12">
                        <ul>
                          <li>
                            <input type="checkbox" name="option1" value="cars" /><span>Cars</span>
                          </li>
                          <li>
                            <input type="checkbox" name="option2" value="aparmtents" /><span
                              >Apartments</span
                            >
                          </li>
                          <li>
                            <input type="checkbox" name="option3" value="shopping" /><span
                              >Shopping</span
                            >
                          </li>
                          <li>
                            <input type="checkbox" name="option4" value="food" /><span
                              >Food &amp; Life</span
                            >
                          </li>
                          <li>
                            <input type="checkbox" name="option5" value="traveling" /><span
                              >Traveling</span
                            >
                          </li>
                        </ul>
                      </div> -->
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
                        <input type="text" v-model="postcode" placeholder="우편번호" />
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
                    </div>
                    <button
                      type="button"
                      v-show="this.$store.state.loginStore.userId"
                      @click="modify"
                    >
                      수정
                    </button>
                    <button
                      type="button"
                      v-show="this.$store.state.loginStore.userId"
                      @click="delete1"
                      style="margin-left: 315px"
                    >
                      탈퇴
                    </button>
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
  name: "ModifyView",

  data() {
    return {
      userId: "",
      userPw: "",
      userName: "",
      userEmail: "",
      phone1: "",
      phone2: "",
      phone3: "",
      userPhone: "",

      postcode: "",
      address: "",
      extraAddress: "",

      // focus
      isUserPwFocus: false,
      isUserNameFocus: false,
      isUserEmailFocus: false,
      isUserPhoneFocus: false,

      // validation
      isUserPwValid: false,
      isUserNameValid: false,
      isUserEmailValid: false,
      isUserPhoneValid: false,
    };
  },

  computed: {
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
  },

  methods: {
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

    async modify() {
      if (!this.isUserPwValid) {
        return this.$alertify.error(
          "비밀번호는 8자리 이상 대소문자, 특수문자, 숫자를 조합하셔야 합니다 "
        );
      }
      if (!this.isUserEmailValid) {
        return this.$alertify.error("이메일이 유효한지 확인하세요.");
      }
      let newUser = {
        userName: this.userName,
        userId: this.$store.state.loginStore.userId,
        userPw: this.userPw,
        userPhone: this.phone1.concat(this.phone2, this.phone3),
        userEmail: this.userEmail,
        userAddress: this.address,
      };
      console.log(newUser);
      await http.put("/users/" + newUser.userId, newUser).then(function (response) {
        console.log(response);
        if (response.data === "success") {
          this.$alertify.alert("수정되었습니다. 마이페이지로 이동합니다");
        }
      });
      this.$router.push("/user");
    },

    async delete1() {
      let newUser = {
        userId: this.$store.state.loginStore.userId,
      };

      await http
        .delete(
          "/users/" + newUser.userId
          //this.$store.state.loginStore.userId
        )
        .then(function (response) {
          console.log(response);
          // if (response.data === "success") {
          // }
        }); // JSON Request, { params : registerObj } X params 를 쓰면 get => query string
      this.$router.push("/");
    },
  },
  created() {
    let userId = this.$store.state.loginStore.userId;
    let userName = this.$store.state.loginStore.userName;
    console.log(userId);
    http
      .get("users/${userId}", {
        params: {
          userId: userId,
          userName: userName,
        },
      })
      .then(({ data }) => {
        console.log(data);
        this.userName = data.userName;
        this.userId = data.userId;
        this.userPw = data.userPw;
        this.userPhone = data.userPhone;
        this.userEmail = data.userEmail;
      });
  },
};
</script>

<style>
.text-center {
  text-align: center;
  padding: 30px;
}
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
