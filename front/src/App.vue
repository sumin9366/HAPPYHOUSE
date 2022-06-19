<template>
  <div>
    <!-- ***** Preloader Start ***** -->
    <div id="js-preloader" class="js-preloader">
      <div class="preloader-inner">
        <span class="dot"></span>
        <div class="dots">
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>
    </div>
    <!-- ***** Preloader End ***** -->

    <header-view />
    <!-- <page-header-view /> -->
    <router-view />
    <footer-view />
  </div>
</template>

<script>
import HeaderView from "./components/HeaderView.vue";
import FooterView from "./components/FooterView.vue";
// import PageHeaderView from "./components/PageHeaderView.vue";

export default {
  name: "App",
  components: {
    HeaderView,
    FooterView,
    // PageHeaderView,
  },
};
</script>

<style></style>
