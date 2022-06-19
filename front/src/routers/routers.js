import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

import HomeView from "@/components/HomeView.vue";
import SearchView from "@/components/SearchView.vue";
import NoticeView from "@/components/NoticeView.vue";
import BoardView from "@/components/BoardView.vue";
import LoginView from "@/components/LoginView.vue";
import RegisterView from "@/components/RegisterView.vue";
import UserModifyView from "@/components/UserModifyView.vue";
import FindPwView from "@/components/FindPwView.vue";
import UserDetailView from "@/components/UserDetailView.vue";
import store from "@/store/store";

import TestView from "@/components/TestView.vue"; // 각종 테스트를 위한 Vue
export default new VueRouter({
  scrollBehavior() {
    // 라우터가 바뀔 때마다 스크롤을 좌상단으로 (부트스트랩 때문인지 모르겠지만 부드럽게 움직임)
    return { x: 0, y: 0 };
  },
  routes: [
    {
      path: "/",
      component: HomeView,
    },
    {
      name: "SearchView",
      path: "/search",
      component: SearchView,
    },
    {
      name: "NoticeView",
      path: "/notice",
      component: NoticeView,
      beforeEnter: (to, from, next) => {
        store.commit("SET_BOARD_SEARCH_WORD", "");
        store.commit("SET_BOARD_MOVE_PAGE", 1);
        // console.log(store.getters.getBoardSearchWord);
        return next();
      },
    },
    {
      name: "BoardView",
      path: "/board",
      component: BoardView,
      beforeEnter: (to, from, next) => {
        store.commit("SET_BOARD_SEARCH_WORD", "");
        store.commit("SET_BOARD_MOVE_PAGE", 1);

        return next();
      },
    },
    {
      name: "LoginView",
      path: "/login",
      component: LoginView,
    },
    {
      name: "RegisterView",
      path: "/register",
      component: RegisterView,
    },
    {
      name: "ModifyView",
      path: "/modify",
      component: UserModifyView,
    },
    {
      name: "FindPwView",
      path: "/findpw",
      component: FindPwView,
    },
    {
      name: "UserDetailView",
      path: "/user",
      component: UserDetailView,
    },
    // 테스트용
    {
      name: "TestView",
      path: "/test",
      component: TestView,
    },
  ],
});
