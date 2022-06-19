<template>
  <!-- ***** Header Area Start ***** -->
  <header
    class="header-area header-sticky wow slideInDown"
    data-wow-duration="0.75s"
    data-wow-delay="0s"
  >
    <div class="container">
      <div class="row">
        <div class="col-12 overflows">
          <nav class="main-nav">
            <!-- ***** Logo Start ***** -->
            <!-- <a href="index.html" class="logo"> </a> -->
            <router-link to="/" class="logo"></router-link>
            <!-- ***** Logo End ***** -->
            <!-- ***** Menu Start ***** -->
            <ul class="nav">
              <li><router-link to="/" class="nav-link">Home</router-link></li>
              <li><router-link to="/search" class="nav-link">거래가 검색</router-link></li>
              <li><router-link to="/notice" class="nav-link">공지사항</router-link></li>
              <li><router-link to="/board" class="nav-link">자유게시판</router-link></li>
              <li v-if="!$store.state.loginStore.isLogin" class="inline-button">
                <div class="main-white-button col-5">
                  <router-link to="/login" class="buttons"
                    ><lord-icon
                      src="https://cdn.lordicon.com/huwchbks.json"
                      trigger="hover"
                      style="width: 48px; height: 48px"
                    >
                    </lord-icon>
                  </router-link>
                </div>
              </li>
              <li v-if="!$store.state.loginStore.isLogin" class="inline-button">
                <div class="main-white-button col-5">
                  <router-link to="/register" class="buttons">
                    <lord-icon
                      src="https://cdn.lordicon.com/puvaffet.json"
                      trigger="hover"
                      style="width: 48px; height: 48px"
                    >
                    </lord-icon
                  ></router-link>
                </div>
              </li>
              <li v-if="$store.state.loginStore.isLogin" class="inline-button">
                <div class="main-white-button col-5">
                  <router-link to="/user" class="buttons">
                    <lord-icon
                      src="https://cdn.lordicon.com/dxjqoygy.json"
                      trigger="hover"
                      style="width: 48px; height: 48px"
                    >
                    </lord-icon
                  ></router-link>
                </div>
              </li>

              <li v-if="$store.state.loginStore.isLogin" class="inline-button">
                <div class="main-white-button col-5">
                  <a href="#" id="logout" @click="logout" class="buttons">
                    <lord-icon
                      src="https://cdn.lordicon.com/moscwhoj.json"
                      trigger="hover"
                      style="width: 48px; height: 48px"
                    >
                    </lord-icon
                  ></a>
                </div>
              </li>
              <li v-show="false"></li>
            </ul>
            <a class="menu-trigger">
              <span>Menu</span>
            </a>
            <!-- ***** Menu End ***** -->
          </nav>
        </div>
      </div>
    </div>
  </header>
  <!-- ***** Header Area End ***** -->
</template>

<script>
export default {
  methods: {
    logout() {
      this.$store.commit("loginStore/SET_LOGIN", {
        isLogin: false,
        userName: "",
        userId: "",
        userPw: "",
        userEmail: "",
        userAddress: "",
        userClsf: "",
        userPhone: "",
      });

      // this.$router.push("/")

      this.$alertify.success("로그아웃에 성공했습니다");
      //console.log(this.$store.state.login.isLogin);
      //console.log(this.$route.path);
      if (
        this.$route.path == "/notice" ||
        this.$route.path == "/board" ||
        this.$route.path == "/register" ||
        this.$route.path == "/modify" ||
        this.$route.path == "/user"
      ) {
        this.$router.push("/");
      }
    },
  },
};
</script>

<style scoped>
.buttons {
  padding: 0px !important;
  width: 48px;
  height: 48px !important;
}
.inline-button {
  padding-left: 10px !important;
  padding-right: 0px !important;
}
.logo {
  width: 184px !important;
}
</style>
