import http from "@/common/axios.js";
import Vue from "vue";
import VueAlertify from "vue-alertify";

Vue.use(VueAlertify);

export default {
  namespaced: true,
  state: {
    selectSidoList: [],
    selectGugunList: [],
    selectDongList: [],
    selectSido: "sidoAll",
    selectGugun: "gugunAll",
    selectDong: "dongAll",

    searchAptName: "",
    lowerValPrice: 0,
    upperValPrice: 150000,

    houseList: [],

    searchInfo: '""의 검색결과',
    limit: 15,
    offset: 0,

    // pagination
    listRowCount: 15,
    pageLinkCount: 10,
    currentPageIndex: 1,
    totalListItemCount: 0,

    // detail
    aptCode: 0,
    aptBuildYear: 0,
    aptName: "",
    address: "",
    roadName: "",
    aptJibun: "",
    bookmarked: "F",

    dealList: [],
    dealCount: 0,
  },
  mutations: {
    SET_HOUSE_LIST(state, list) {
      state.houseList = list;
    },

    SET_SEARCH_INFO(state, info) {
      state.searchInfo = info;
    },

    SET_SIDO_LIST(state, list) {
      let retSidoList = [{ code: "sidoAll", name: "시/도 전체" }];

      list.forEach((el) => {
        retSidoList.push({ code: `${el.sidoCode}`, name: `${el.sidoName}` });
      });
      console.log(retSidoList);
      state.selectSidoList = retSidoList;
    },
    SET_GUGUN_LIST(state, list) {
      let retGugunList = [{ code: "gugunAll", name: "시/군/구 전체" }];

      list.forEach((el) => {
        retGugunList.push({ code: `${el.gugunCode}`, name: `${el.gugunName}` });
      });
      state.selectGugunList = retGugunList;
    },
    SET_DONG_LIST(state, list) {
      let retDongList = [{ code: "dongAll", name: "동 전체" }];

      list.forEach((el) => {
        retDongList.push({ code: `${el.dongCode}`, name: `${el.dongName}` });
      });
      state.selectDongList = retDongList;
    },

    SET_SIDO_ALL(state) {
      state.selectSido = "sidoAll";
    },
    SET_GUGUN_ALL(state) {
      state.selectGugun = "gugunAll";
    },
    SET_DONG_ALL(state) {
      state.selectDong = "dongAll";
    },

    SET_HOUSE_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.totalListItemCount = count;
    },
    SET_HOUSE_MOVE_PAGE(state, pageIndex) {
      state.offset = (pageIndex - 1) * state.listRowCount;
      state.currentPageIndex = pageIndex;
    },
    SET_HOUSE_DETAIL(state, payload) {
      state.aptCode = payload.aptCode;
      state.aptBuildYear = payload.aptBuildYear;
      state.aptName = payload.aptName;
      state.address = payload.address;
      state.roadName = payload.roadName;
      state.aptJibun = payload.aptJibun;
      state.dealList = payload.housedealList;
      state.bookmarked = payload.bookmarked;
    },
    CHANGE_BOOKMARKED(state) {
      if (state.bookmarked == "F") {
        state.bookmarked = "T";
      } else if (state.bookmarked == "T") {
        state.bookmarked = "F";
      }
    },
    SET_LOWER_VAL_PRICE(state, val) {
      state.lowerValPrice = val;
    },
    SET_UPPER_VAL_PRICE(state, val) {
      state.upperValPrice = val;
    },
  },
  actions: {
    async getSelectSido(context) {
      // let fetchOptions = {
      //   headers: {
      //     async: "true",
      //   },
      // };
      try {
        // let response = await fetch("/sidos", fetchOptions);
        // let data = await response.json();
        let { data } = await http.get("/sidos");
        console.log(data);
        context.commit("SET_SIDO_LIST", data);

        context.commit("SET_SIDO_ALL");
        context.commit("SET_GUGUN_LIST", []);
        context.commit("SET_GUGUN_ALL");
        context.commit("SET_DONG_LIST", []);
        context.commit("SET_DONG_ALL");
      } catch (error) {
        console.error(error);
        this.$alertify.error("시/도 정보를 가져오는 과정에 문제가 생겼습니다.");
      }
    },
    async getSelectGugun(context) {
      try {
        // let response = await fetch("/guguns?sidoCode=" + this.state.selectSido, fetchOptions);
        // let data = await response.json();

        let { data } = await http.get("/guguns?sidoCode=" + this.state.searchStore.selectSido);
        console.log(data);
        context.commit("SET_GUGUN_LIST", data);
      } catch (error) {
        console.error(error);
        this.$alertify.error("구/군 정보를 가져오는 과정에 문제가 생겼습니다.");
      }
    },
    async getSelectDong(context) {
      try {
        // let response = await fetch("/dongs?gugunCode=" + this.state.selectGugun);
        // let data = await response.json();

        let { data } = await http.get("/dongs?gugunCode=" + this.state.searchStore.selectGugun);
        console.log(data);
        context.commit("SET_DONG_LIST", data);
      } catch (error) {
        console.error(error);
        this.$alertify.error("동 정보를 가져오는 과정에 문제가 생겼습니다.");
      }
    },
  },
  getters: {
    selectSidoList: function (state) {
      return state.selectSidoList;
    },
    selectGugunList: function (state) {
      return state.selectGugunList;
    },
    selectDongList: function (state) {
      return state.selectDongList;
    },
    selectSido: function (state) {
      return state.selectSido;
    },
    selectGugun: function (state) {
      return state.selectGugun;
    },
    selectDong: function (state) {
      return state.selectDong;
    },
    searchAptName: function (state) {
      return state.searchAptName;
    },
    houseList: function (state) {
      return state.houseList;
    },
    limit: function (state) {
      return state.limit;
    },
    offset: function (state) {
      return state.offset;
    },

    aptCode: function (state) {
      return state.aptCode;
    },
    aptBuildYear: function (state) {
      return state.aptBuildYear;
    },
    aptName: function (state) {
      return state.aptName;
    },
    address: function (state) {
      return state.address;
    },
    roadName: function (state) {
      return state.roadName;
    },
    aptJibun: function (state) {
      return state.aptJibun;
    },
    dealList: function (state) {
      return state.dealList;
    },
    dealCount: function (state) {
      return state.dealCount;
    },
    bookmarked: function (state) {
      return state.bookmarked;
    },

    // pagination
    pageCount: function (state) {
      return Math.ceil(state.totalListItemCount / state.listRowCount);
    },
    startPageIndex: function (state) {
      if (state.currentPageIndex % state.pageLinkCount == 0) {
        //10, 20...맨마지막
        return (state.currentPageIndex / state.pageLinkCount - 1) * state.pageLinkCount + 1;
      } else {
        return Math.floor(state.currentPageIndex / state.pageLinkCount) * state.pageLinkCount + 1;
      }
    },
    endPageIndex: function (state, getters) {
      let ret = 0;
      if (state.currentPageIndex % state.pageLinkCount == 0) {
        //10, 20...맨마지막
        ret =
          (state.currentPageIndex / state.pageLinkCount - 1) * state.pageLinkCount +
          state.pageLinkCount;
      } else {
        ret =
          Math.floor(state.currentPageIndex / state.pageLinkCount) * state.pageLinkCount +
          state.pageLinkCount;
      }
      // 위 오류나는 코드를 아래와 같이 비교해서 처리
      return ret > getters.pageCount ? getters.pageCount : ret;
    },
    prev: function (state) {
      if (state.currentPageIndex <= state.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    next: function (state, getters) {
      if (
        Math.floor(getters.pageCount / state.pageLinkCount) * state.pageLinkCount <
        state.currentPageIndex
      ) {
        return false;
      } else {
        return true;
      }
    },
    searchInfo: function (state) {
      return state.searchInfo;
    },
    lowerValPrice: function (state) {
      return state.lowerValPrice;
    },
    upperValPrice: function (state) {
      return state.upperValPrice;
    },
  },
};
