<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>&nbsp;</h6>
              <h2>Check User</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="contact-page">
      <div class="container">
        <div class="container mt-5">
          <div class="row d-flex justify-content-center">
            <div class="col-md-7">
              <div class="card p-3 py-4">
                <div class="text-center">
                  <img
                    src="https://www.pngfind.com/pngs/m/300-3001322_house-with-garden-emoji-icon-house-tree-icon.png"
                    width="20"
                    class="rounded-circle"
                  />
                </div>

                <div class="text-center mt-3">
                  <h5 class="mt-2 mb-0">
                    {{ this.$store.state.loginStore.userName }}
                  </h5>
                  <span>{{ this.$store.state.loginStore.userEmail }}</span>

                  <div class="px-4 mt-1">
                    <p class="fonts">
                      {{ this.$store.state.loginStore.userAddress }}
                    </p>
                  </div>

                  <ul class="social-list">
                    <li><i class="fa fa-facebook"></i></li>
                    <li><i class="fa fa-dribbble"></i></li>
                    <li><i class="fa fa-instagram"></i></li>
                    <li><i class="fa fa-linkedin"></i></li>
                    <li><i class="fa fa-google"></i></li>
                  </ul>

                  <div>
                    <button
                      class="btn btn-primary px-4 ms-3"
                      v-show="this.$store.state.loginStore.userId"
                      @click="modify"
                      style="background-color: #7e838f"
                    >
                      수정하기
                    </button>
                  </div>
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

export default {
  name: "UserDetailView",
  methods: {
    modify() {
      this.$router.push("/modify");
    },
  },
};
</script>

<style scoped>
@import url("https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css");
@import url("https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css");

body {
  background: #eee;
}

.card {
  border: none;

  position: relative;
  overflow: hidden;
  border-radius: 8px;
  cursor: pointer;
}

.card:before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 4px;
  height: 100%;
  background-color: #bdbfc5;
  transform: scaleY(1);
  transition: all 0.5s;
  transform-origin: bottom;
}

.card:after {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 4px;
  height: 100%;
  background-color: #7e838f;
  transform: scaleY(0);
  transition: all 0.5s;
  transform-origin: bottom;
}

.card:hover::after {
  transform: scaleY(1);
}

.fonts {
  font-size: 11px;
}

.social-list {
  display: flex;
  list-style: none;
  justify-content: center;
  padding: 0;
}

.social-list li {
  padding: 10px;
  color: #7e838f;
  font-size: 19px;
}

.buttons button:nth-child(1) {
  border: 1px solid #7e838f !important;
  color: #7e838f;
  height: 40px;
}

.buttons button:nth-child(1):hover {
  border: 1px solid #7e838f !important;
  color: #fff;
  height: 40px;
  background-color: #7e838f;
}

.buttons button:nth-child(2) {
  border: 1px solid #7e838f !important;
  background-color: #7e838f;
  color: #fff;
  height: 40px;
}
</style>
