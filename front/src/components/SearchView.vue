<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>&nbsp;</h6>
              <h2>거래가 검색</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="category-post">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="naccs">
              <div class="grid">
                <div class="row">
                  <div id="content">
                    <map-view
                      v-on:map-called="getHouseDetail"
                      v-on:page-called="movePage"
                      v-on:bookmark-called="getHouseByBookmark"
                      v-on:house-list-called="getHouseList"
                      v-on:myhouse-called="getMyHouse"
                      ref="map"
                    ></map-view>
                  </div>
                  <div class="container mt-2">
                    <table class="table table-borderless card-1 p-4 cursor-pointer">
                      <thead>
                        <tr class="border-bottom">
                          <th class="col-lg-3">
                            <span class="ml-3">아파트 이름</span>
                          </th>
                          <th class="col-lg-3">
                            <span class="ml-2">주소</span>
                          </th>
                          <th class="col-lg-2">
                            <span class="ml-2">건축연도</span>
                          </th>
                          <th class="col-lg-2">
                            <span class="ml-2">최근 거래가</span>
                          </th>
                          <th class="col-lg-1">
                            <span class="ml-2">북마크</span>
                          </th>
                        </tr>
                      </thead>
                      <tbody class="house-tbody">
                        <tr
                          class="border-bottom"
                          v-for="(house, index) in houseList"
                          :key="index"
                          @click="houseDetail(house.aptCode, index)"
                        >
                          <td>
                            <div class="p-2 d-flex flex-row align-items-center mb-2">
                              <div class="d-flex flex-column ml-2">
                                <span class="d-block font-weight-bold">{{ house.aptName }}</span>
                              </div>
                            </div>
                          </td>
                          <td>
                            <div class="p-2 d-flex flex-column">
                              <span>{{ house.address }}</span>
                              <small class="mt-1">({{ house.roadName }})</small>
                            </div>
                          </td>
                          <td>
                            <div class="p-2">
                              <span class="font-weight-bold">{{ house.aptBuildYear }}년</span>
                            </div>
                          </td>
                          <td>
                            <div class="p-2">
                              <span class="d-block font-weight-bold"
                                >{{ house.recentDealAmount | changedAmount }}억원</span
                              >
                              <small
                                >{{ house.recentDealArea }}m²,&nbsp;&nbsp;{{
                                  house.recentDealDate
                                }}</small
                              >
                            </div>
                          </td>
                          <td>
                            <div class="p-2 icons">
                              <div @click="btnBookmark(index, house.bookmarked)" class="s3">
                                <img
                                  :id="`bookmark-${index}`"
                                  :src="require(`@/assets/${house.bookmarked}_star.png`)"
                                />
                              </div>
                            </div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                  <search-pagination-view v-on:call-parent="movePage"></search-pagination-view>
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
import MapView from "@/components/MapView.vue";
import http from "@/common/axios.js";
import { mapGetters, mapActions, mapMutations } from "vuex";
import SearchPaginationView from "./SearchPaginationView.vue";
import Vue from "vue";
import VueAlertify from "vue-alertify";

Vue.use(VueAlertify);

export default {
  components: { MapView, SearchPaginationView },
  mounted() {
    this.getHouseList();
  },
  computed: {
    ...mapGetters("searchStore", [
      "selectSido",
      "selectGugun",
      "selectDong",
      "searchAptName",
      "houseList",
      "searchInfo",
      "limit",
      "offset",
      "lowerValPrice",
      "upperValPrice",
    ]),
    ...mapGetters("loginStore", ["userId"]),
  },
  methods: {
    ...mapActions("searchStore", ["addBookmark"]),
    ...mapMutations("searchStore", [
      "SET_LOGIN",
      "SET_HOUSE_LIST",
      "SET_HOUSE_TOTAL_LIST_ITEM_COUNT",
      "SET_HOUSE_MOVE_PAGE",
      "SET_HOUSE_DETAIL",
    ]),
    houseDetail(aptCode, index) {
      console.log(aptCode);
      this.getHouseDetail(aptCode);
      var offsetTop =
        window.pageYOffset +
        document.querySelector("#btnSearchDiv").getBoundingClientRect().top -
        80;
      window.scrollTo({ top: offsetTop, left: 0, behavior: "smooth" });
      this.$refs.map.clickDetailFromSuper(index);
    },
    // pagination
    movePage(pageIndex) {
      console.log("SearchViewVue : movePage : pageIndex : " + pageIndex);
      this.SET_HOUSE_MOVE_PAGE(pageIndex);
      this.getHouseList();
    },

    ////////// 1234

    ////////// 1234

    getHouseList: async function () {
      let searchUrl = `?`;
      if (this.searchAptName.replace(/ /g, "") != "") {
        searchUrl += `aptName=${this.searchAptName}&`;
      }
      if (this.selectSido != "sidoAll") {
        let code = "";
        if (this.selectGugun == "gugunAll") {
          code = this.selectSido;
        } else if (this.selectDong == "dongAll") {
          code = this.selectGugun;
        } else {
          code = this.selectDong;
        }
        searchUrl += `dongCode=${code}&`;
      }
      if (this.lowerValPrice > 0) {
        searchUrl += `priceMin=${this.lowerValPrice}&`;
      }
      if (this.upperValPrice < 150000) {
        searchUrl += `priceMax=${this.upperValPrice}&`;
      }
      searchUrl += `limit=${this.limit}&offset=${this.offset}`;
      if (this.userId != undefined) {
        searchUrl += `&userId=${this.userId}`;
      }
      try {
        let { data } = await http.get("/houses/search" + searchUrl);
        console.log(data);
        //makeHouseList(data);
        this.SET_HOUSE_LIST(data.list);
        this.SET_HOUSE_TOTAL_LIST_ITEM_COUNT(data.count);
        this.$refs.map.makeSearchInfo();
        // makeMap(data);
      } catch (error) {
        console.error(error);
        this.$alertify.error("아파트 조회 과정에 문제가 생겼습니다.");
      }
    },
    getMyHouse: async function () {
      let searchUrl = `?userId=${this.userId}`;

      try {
        let { data } = await http.get("/houses/myhouse" + searchUrl);
        console.log(data);
        //makeHouseList(data);
        this.SET_HOUSE_LIST(data.list);
        this.SET_HOUSE_TOTAL_LIST_ITEM_COUNT(data.count);
        this.$refs.map.makeSearchInfo();
        // makeMap(data);
      } catch (error) {
        console.error(error);
        this.$alertify.error(
          "잘못된 주소를 가지고 계시거나 데이터를 부르는 과정에 오류가 생겼습니다."
        );
      }
    },
    getHouseByBookmark: async function () {
      let searchUrl = `?bookmark=apt&userId=${this.userId}&limit=${this.limit}&offset=${this.offset}`;
      try {
        let { data } = await http.get("/houses/search" + searchUrl);
        console.log(data);
        //makeHouseList(data);
        this.SET_HOUSE_MOVE_PAGE(1);
        this.SET_HOUSE_LIST(data.list);
        this.SET_HOUSE_TOTAL_LIST_ITEM_COUNT(data.count);
        this.$refs.map.makeSearchInfo();
        // makeMap(data);
      } catch (error) {
        console.error(error);
        this.$alertify.error("아파트 조회 과정에 문제가 생겼습니다.");
      }
    },

    getHouseDetail: async function (aptCode) {
      let searchUrl = `?aptCode=${aptCode}&limit=100&offset=0`;
      if (this.userId != undefined) {
        searchUrl += `&userId=${this.userId}`;
      }

      try {
        let { data } = await http.get("/houses/detail" + searchUrl);
        console.log(data);
        this.SET_HOUSE_DETAIL(data.dto);
      } catch (error) {
        console.error(error);
        this.$alertify.error("아파트 조회 과정에 문제가 생겼습니다.");
      }
    },

    btnBookmark(index, bookmarked) {
      if (this.userId == "" || this.userId == undefined) {
        this.$alertify.error("로그인이 필요한 서비스입니다.");
      } else {
        if (bookmarked == "F") {
          this.$refs.map.addBookmark(index);
        } else if (bookmarked == "T") {
          this.$refs.map.removeBookmark(index);
        }
      }
    },
  },
  filters: {
    changedAmount: function (value) {
      return (+(Math.round(value / 10000 + "e+2") + "e-2"))
        .toString()
        .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style scoped>
form > .row {
  background-color: #f7f7f7;
}

.div-button {
  padding-right: 0px;
}

.card-1 {
  border: none;
  border-radius: 10px;
  width: 100%;
  background-color: #fff;
}

.icons div {
  margin-left: 5px;
  width: 40px;
  height: 40px;
  background-color: #26a3ff;
  border-radius: 7px;
}

.icons div img {
  height: 38px;
  width: auto;
  margin-top: 1px;
  margin-left: 1px;
}

.cursor-pointer {
  cursor: pointer;
}

.cursor-pointer > thead th:hover {
  background-color: #e4e4e4;
}

.cursor-pointer > tbody > tr:hover {
  background-color: #e1f9e1;
}

.table-borderless > thead {
  width: 100% !important;
  background-color: #f7f7f7;
}

.house-tbody tr td {
  padding: 0 8px !important;
}

.category-post > .container {
  max-width: 1800px;
}
</style>
