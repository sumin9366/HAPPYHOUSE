<template>
  <div id="wrapper" style="height: 89vh">
    <div id="map" style="width: 100%; height: 100%"></div>
    <div id="searchDiv" class="float-start">
      <form id="search-form" name="gs" method="submit" role="search" action="#">
        <div class="row">
          <div class="col-search-1 align-self-center">
            <fieldset>
              <select
                id="sido"
                name="sido"
                class="form-select"
                aria-label="Area"
                v-model="$store.state.searchStore.selectSido"
                @change="onSelectSido"
              >
                <option v-for="(option, index) in selectSidoList" :key="index" :value="option.code">
                  {{ option.name }}
                </option>
              </select>
            </fieldset>
          </div>
          <div class="col-search-2 align-self-center">
            <fieldset>
              <select
                id="gugun"
                name="gugun"
                class="form-select"
                aria-label="Area"
                v-model="$store.state.searchStore.selectGugun"
                @change="onSelectGugun"
              >
                <option
                  v-for="(option, index) in selectGugunList"
                  :key="index"
                  :value="option.code"
                >
                  {{ option.name }}
                </option>
              </select>
            </fieldset>
          </div>
          <div class="col-search-3 align-self-center">
            <fieldset>
              <select
                id="dong"
                name="dong"
                class="form-select"
                aria-label="Default select example"
                v-model="$store.state.searchStore.selectDong"
              >
                <option v-for="(option, index) in selectDongList" :key="index" :value="option.code">
                  {{ option.name }}
                </option>
              </select>
            </fieldset>
          </div>
          <div class="col-search-4 align-self-center">
            <fieldset>
              <input hidden="hidden" />
              <input
                type="text"
                class="searchText"
                v-model="$store.state.searchStore.searchAptName"
                placeholder="아파트 이름으로 검색"
                autocomplete="on"
              />
            </fieldset>
          </div>
          <div id="btnSearchDiv" class="col-search-5 div-button p-0">
            <fieldset>
              <button type="button" class="main-button" @click="btnSearch">
                <i class="fa fa-search"></i>Search
              </button>
            </fieldset>
          </div>
        </div>
      </form>
    </div>
    <div id="detailSearch" class="when-closed">
      <div
        id="detailSearchTitle"
        class="py-2 px-3 none-text-drag moving-title"
        style="cursor: move"
      >
        상세 검색 설정
        <div @click="btnDetailSearchOpen" class="open-close">{{ detailSearchOpenClose }}</div>
      </div>
      <div v-show="isDetailSearchOpen" class="scrolling detail-search-div">
        <div>최근 거래가 기준</div>
        <div>
          <span class="multi-range">
            <input
              type="range"
              min="0"
              max="150000"
              v-model="$store.state.searchStore.lowerValPrice"
              @oninput="lower"
              id="lower"
            />
            <input
              type="range"
              min="0"
              max="150000"
              v-model="$store.state.searchStore.upperValPrice"
              @oninput="upper"
              id="upper"
            />
          </span>
        </div>
        <div>
          <input
            type="text"
            v-model="$store.state.searchStore.lowerValPrice"
            class="col-3 mr-0 pr-0 pl-3"
          /><span class="col-3 m-0 p-0">만원 이상</span>
          <input type="text" v-model="getUpperValPrice" class="col-3 mr-0 pr-0 pl-3" /><span
            class="col-3 m-0 p-0"
            >만원 이하</span
          >
        </div>
        <div>
          <button @click="detailSearchBookmark" type="button" class="btn btn-outline-primary">
            북마크
          </button>
          <button @click="myHouse" type="button" class="btn btn-outline-success">내 집</button>
          <button @click="rollbackDetail" type="button" class="btn btn-outline-info">
            상세 검색 초기화
          </button>
        </div>
      </div>
    </div>
    <div id="list">
      <div id="listTitle" class="py-2 px-3 none-text-drag moving-title" style="cursor: move">
        아파트 목록
        <div @click="btnListOpen" class="open-close">{{ listOpenClose }}</div>
      </div>
      <div v-show="isListOpen" id="listScroll" class="scrolling">
        <ul class="list-group">
          <li
            v-for="(house, index) in houseList"
            :key="index"
            @click="clickDetail(index, house.aptCode)"
            v-on:mouseover="openInfowindow(index)"
            v-on:mouseout="closeInfowindow(index)"
            class="list-group-item justify-content-between align-items-center"
          >
            <table>
              <tr>
                <td class="s1 pb-2">{{ house.aptName }}</td>
                <td class="s2 pb-2">
                  {{
                    (+(Math.round(house.recentDealAmount / 10000 + "e+2") + "e-2"))
                      .toString()
                      .replace(/\B(?=(\d{3})+(?!\d))/g, ",")
                  }}억원
                </td>
                <td class="s3 pb-2">
                  <div class="bookmark-div">
                    <img
                      :id="`bookmark-${index}`"
                      :src="require(`@/assets/${house.bookmarked}_star.png`)"
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="2">{{ house.address }}</td>
                <td>{{ house.aptBuildYear }}</td>
              </tr>
            </table>
            <!-- <span class="s3"><i class="fa fa-phone text-danger"></i></span> -->
            <!-- <span class="badge bg-primary rounded-pill">14</span> -->
          </li>
        </ul>
      </div>
    </div>
    <div id="detail" class="when-closed">
      <div id="detailTitle" class="py-2 px-3 none-text-drag moving-title" style="cursor: move">
        상세 정보 및 거래 목록
        <div @click="btnDetailOpen" class="open-close">{{ detailOpenClose }}</div>
        <div @click="btnDetailUp" class="open-close mr-3">위로</div>
      </div>
      <div v-show="isDetailOpen" id="detailScroll" class="scrolling">
        <div class="house-detail">
          <table class="detail-table">
            <thead>
              <tr>
                <th>
                  <div class="bookmark-div bookmark-div-detail">
                    <img
                      id="bookmark-detail"
                      @click="btnBookmark(openedInfowindowIndex, bookmarked)"
                      :src="require(`@/assets/${bookmarked}_star.png`)"
                    />
                  </div>
                </th>
                <th colspan="2">{{ aptName }}</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>건축연도</td>
                <td>{{ aptBuildYear }}년</td>
                <td></td>
              </tr>
              <tr>
                <td>법정동 주소</td>
                <td>{{ address }}</td>
                <td></td>
              </tr>
              <tr>
                <td>지번 | 도로명</td>
                <td>{{ aptJibun }} | {{ roadName }}</td>
                <td></td>
              </tr>
              <tr>
                <td>평점</td>
                <td></td>
                <td></td>
              </tr>
            </tbody>
          </table>
        </div>
        <ul class="list-group">
          <li
            v-for="(deal, index) in dealList"
            :key="index"
            class="list-group-item d-flex justify-content-between align-items-center"
          >
            <span class="c1">{{ deal.dealYear }}.{{ deal.dealMonth }}.{{ deal.dealDay }}</span>
            <span class="c2">{{ deal.dealAmount | changedAmount }}억원</span>
            <span class="c3">{{ deal.dealArea }}m²</span>
            <span class="c4">{{ deal.dealFloor }}층</span>
            <!-- <span class="badge bg-primary rounded-pill">14</span> -->
          </li>
        </ul>
      </div>
    </div>
    <div class="content-middle content-middle-info">
      {{ searchInfo }}
    </div>
    <div class="content-middle content-middle-pagination">
      <search-pagination-view v-on:call-parent="movePage"></search-pagination-view>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import SearchPaginationView from "./SearchPaginationView.vue";
import http from "@/common/axios.js";
import Vue from "vue";
import VueAlertify from "vue-alertify";

Vue.use(VueAlertify);

export default {
  components: { SearchPaginationView },
  data() {
    return {
      map: null,
      centerLat: 37.6497106,
      centerLng: 127.0252895,
      positions: [], // 마커를 표시할 위치와 title 객체 배열입니다
      markers: [], // 지도에 표시된 마커 객체를 가지고 있을 배열입니다
      infowindows: [],
      openedInfowindowIndex: 0,

      listOpenClose: "닫기",
      isListOpen: true,
      detailOpenClose: "열기",
      isDetailOpen: false,
      detailSearchOpenClose: "열기",
      isDetailSearchOpen: false,
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=9faad6c14d3b4025089177cf505db70b";
      document.head.appendChild(script);
    }
    this.getSelectSido();
    var obox = document.getElementById("wrapper");
    var odrag1 = document.getElementById("list");
    var odrag2 = document.getElementById("detailSearch");
    var odrag3 = document.getElementById("detail");
    var listTitle = document.getElementById("listTitle");
    var detailSearchTitle = document.getElementById("detailSearchTitle");
    var detailTitle = document.getElementById("detailTitle");
    var flag1 = false;
    var flag2 = false;
    var flag3 = false;
    var x, y;
    var ol, ot;
    listTitle.onmousedown = function (ev) {
      var ev1 = window.event || ev;
      flag1 = true;
      x = ev1.clientX;
      y = ev1.clientY;
      ol = odrag1.offsetLeft;
      ot = odrag1.offsetTop;
    };
    detailSearchTitle.onmousedown = function (ev) {
      var ev2 = window.event || ev;
      flag2 = true;
      x = ev2.clientX;
      y = ev2.clientY;
      ol = odrag2.offsetLeft;
      ot = odrag2.offsetTop;
    };
    detailTitle.onmousedown = function (ev) {
      var ev3 = window.event || ev;
      flag3 = true;
      x = ev3.clientX;
      y = ev3.clientY;
      ol = odrag3.offsetLeft;
      ot = odrag3.offsetTop;
    };
    document.onmousemove = function (ev) {
      if (flag1 == true) {
        var ev1 = window.event || ev;
        var _x1, _y1;
        _x1 = ev1.clientX - x + ol;
        _y1 = ev1.clientY - y + ot;
        if (_x1 < 0) _x1 = 0;
        if (_y1 < 0) _y1 = 0;
        if (_x1 > obox.offsetWidth - odrag1.offsetWidth)
          _x1 = obox.offsetWidth - odrag1.offsetWidth;
        if (_y1 > obox.offsetHeight - odrag1.offsetHeight)
          _y1 = obox.offsetHeight - odrag1.offsetHeight;

        odrag1.style.left = _x1 + "px";
        odrag1.style.top = _y1 + "px";
      } else if (flag2 == true) {
        var ev2 = window.event || ev;
        var _x2, _y2;
        _x2 = ev2.clientX - x + ol;
        _y2 = ev2.clientY - y + ot;
        if (_x2 < 0) _x2 = 0;
        if (_y2 < 0) _y2 = 0;
        if (_x2 > obox.offsetWidth - odrag2.offsetWidth)
          _x2 = obox.offsetWidth - odrag2.offsetWidth;
        if (_y2 > obox.offsetHeight - odrag2.offsetHeight)
          _y2 = obox.offsetHeight - odrag2.offsetHeight;

        odrag2.style.left = _x2 + "px";
        odrag2.style.top = _y2 + "px";
      } else if (flag3 == true) {
        var ev3 = window.event || ev;
        var _x3, _y3;
        _x3 = ev3.clientX - x + ol;
        _y3 = ev3.clientY - y + ot;
        if (_x3 < 0) _x3 = 0;
        if (_y3 < 0) _y3 = 0;
        if (_x3 > obox.offsetWidth - odrag3.offsetWidth)
          _x3 = obox.offsetWidth - odrag3.offsetWidth;
        if (_y3 > obox.offsetHeight - odrag3.offsetHeight)
          _y3 = obox.offsetHeight - odrag3.offsetHeight;

        odrag3.style.left = _x3 + "px";
        odrag3.style.top = _y3 + "px";
      }
    };
    var $this = this;
    var divMap = document.querySelector("#map");
    document.onmouseup = function () {
      $this.closeInfowindow2($this.openedInfowindowIndex);
      flag1 = false;
      flag2 = false;
      flag3 = false;
      if (odrag1.offsetTop + odrag1.offsetHeight > divMap.offsetTop + divMap.offsetHeight) {
        if (odrag1.offsetHeight < divMap.offsetHeight) {
          odrag1.style.top = divMap.offsetTop + divMap.offsetHeight - odrag1.offsetHeight + "px";
        } else {
          odrag1.style.height = divMap.offsetHeight - 1 + "px";
          odrag1.style.top = 0 + "px";
        }
      } else if (odrag2.offsetTop + odrag2.offsetHeight > divMap.offsetTop + divMap.offsetHeight) {
        if (odrag2.offsetHeight < divMap.offsetHeight) {
          odrag2.style.top = divMap.offsetTop + divMap.offsetHeight - odrag2.offsetHeight + "px";
        } else {
          odrag2.style.height = divMap.offsetHeight - 1 + "px";
          odrag2.style.top = 0 + "px";
        }
      } else if (odrag3.offsetTop + odrag3.offsetHeight > divMap.offsetTop + divMap.offsetHeight) {
        if (odrag3.offsetHeight < divMap.offsetHeight) {
          odrag3.style.top = divMap.offsetTop + divMap.offsetHeight - odrag3.offsetHeight + "px";
        } else {
          odrag3.style.height = divMap.offsetHeight - 1 + "px";
          odrag3.style.top = 0 + "px";
        }
      }
    };
  },
  computed: {
    ...mapGetters("searchStore", [
      "selectSidoList",
      "selectGugunList",
      "selectDongList",
      "selectSido",
      "selectGugun",
      "selectDong",
      "searchAptName",
      "houseList",
      "searchInfo",
      "aptCode",
      "aptBuildYear",
      "aptName",
      "address",
      "roadName",
      "aptJibun",
      "dealList",
      "bookmarked",
      "lowerValPrice",
      "upperValPrice",
    ]),
    ...mapGetters("loginStore", ["userId"]),
    check_detail() {
      return this.aptCode;
    },
    check_list() {
      return this.houseList;
    },
    getUpperValPrice() {
      if (this.upperValPrice >= 150000) {
        return "제한없음";
      } else {
        return this.upperValPrice;
      }
    },
    check_upperValPrice() {
      return this.upperValPrice;
    },
    check_lowerValPrice() {
      return this.lowerValPrice;
    },
  },
  watch: {
    upperValPrice() {
      this.upper();
    },
    lowerValPrice() {
      this.lower();
    },
    houseList() {
      this.updateMap();
    },
    check_detail() {
      if (!this.isDetailOpen) {
        this.btnDetailOpen();
      }
      document.getElementById("detailScroll").scrollTop = 0;
    },
    check_list() {
      document.getElementById("listScroll").scrollTop = 0;
    },
    openedInfowindowIndex: function (newValue) {
      this.openInfowindow2(newValue);
    },
  },
  methods: {
    ...mapActions("searchStore", [
      "addBookmark",
      "removeBookmark",
      "getSelectSido",
      "getSelectGugun",
      "getSelectDong",
    ]),
    ...mapMutations("searchStore", [
      "SET_SIDO_LIST",
      "SET_GUGUN_LIST",
      "SET_DONG_LIST",
      "SET_SIDO_ALL",
      "SET_GUGUN_ALL",
      "SET_DONG_ALL",
      "SET_HOUSE_MOVE_PAGE",
      "SET_SEARCH_INFO",
      "CHANGE_BOOKMARKED",
      "SET_LOWER_VAL_PRICE",
      "SET_UPPER_VAL_PRICE",
    ]),
    onSelectSido: function () {
      if (this.selectSido == "sidoAll") {
        this.SET_GUGUN_LIST([]);
        this.SET_GUGUN_ALL();
        this.SET_DONG_LIST([]);
        this.SET_DONG_ALL();
      } else {
        this.getSelectGugun();
        this.SET_GUGUN_ALL();
        this.SET_DONG_LIST([]);
        this.SET_DONG_ALL();
      }
    },
    onSelectGugun: function () {
      if (this.selectGugun == "gugunAll") {
        this.SET_DONG_LIST([]);
        this.SET_DONG_ALL();
      } else {
        this.getSelectDong();
        this.SET_DONG_ALL();
      }
    },
    makeSidoList: function (list) {
      let retSidoList = [{ code: "sidoAll", name: "시/도 전체" }];

      list.forEach((el) => {
        retSidoList.push({ code: `${el.code}`, name: `${el.name}` });
      });
      console.log(retSidoList);
      return retSidoList;
    },
    makeGugunList: function (list) {
      let retGugunList = [{ code: "gugunAll", name: "시/군/구 전체" }];

      list.forEach((el) => {
        retGugunList.push({ code: `${el.code}`, name: `${el.name}` });
      });
      return retGugunList;
    },
    makeDongList: function (list) {
      let retDongList = [{ code: "dongAll", name: "동 전체" }];

      list.forEach((el) => {
        retDongList.push({ code: `${el.code}`, name: `${el.name}` });
      });
      return retDongList;
    },
    initMap() {
      var mapContainer = document.getElementById("map"), // 이미지 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(this.centerLat, this.centerLng), // 이미지 지도의 중심좌표
          level: 3, // 이미지 지도의 확대 레벨
        };
      this.map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
    },
    updateMap() {
      // 기존 marker들을 지우고 갱신
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.positions = [];
      this.markers = [];
      this.infowindows = [];

      this.houseList.forEach((el) => {
        this.positions.push({
          content: `<span>${el.aptName}<br>최근 거래가: ${(+(
            Math.round(el.recentDealAmount / 10000 + "e+2") + "e-2"
          ))
            .toString()
            .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}억</span>`,
          aptCode: el.aptCode,
          latlng: new kakao.maps.LatLng(parseFloat(el.aptLat), parseFloat(el.aptLng)),
        });
      });
      // 마커 이미지의 이미지 주소입니다 (생략 가능)
      //var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

      // 지도를 재설정할 범위정보를 가지고 있을 LatLngBounds 객체를 생성합니다
      var bounds = new kakao.maps.LatLngBounds();

      for (i = 0; i < this.positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다 (생략 가능)
        //var imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다 (생략 가능)
        //var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: this.positions[i].latlng, // 마커를 표시할 위치
          clickable: true,
          //image : markerImage, // 마커 이미지 (생략 가능)
        });

        this.markers.push(marker);

        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: this.positions[i].content, // 인포윈도우에 표시할 내용
        });

        this.infowindows.push(infowindow);

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        kakao.maps.event.addListener(
          marker,
          "mouseover",
          this.makeOverListener(this.map, marker, infowindow, i, this)
        );
        kakao.maps.event.addListener(marker, "mouseout", this.makeOutListener(infowindow, i, this));
        kakao.maps.event.addListener(marker, "click", this.makeClickListener(i, this));

        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(this.positions[i].latlng);
      }

      // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
      // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
      this.map.setBounds(bounds);
    },
    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
    makeOverListener(map, marker, infowindow, index, vue) {
      return function () {
        if (vue.openedInfowindowIndex != index) {
          infowindow.open(map, marker);
        }
      };
    },
    // 인포윈도우를 닫는 클로저를 만드는 함수입니다
    makeOutListener(infowindow, index, vue) {
      return function () {
        if (vue.openedInfowindowIndex != index) {
          infowindow.close();
        }
      };
    },
    makeClickListener(index, vue) {
      return function () {
        vue.setCenter(index);
        vue.openedInfowindowIndex = index;
        vue.$emit("map-called", vue.positions[index].aptCode);
      };
    },
    openInfowindow(index) {
      if (this.openedInfowindowIndex != index) {
        this.infowindows[index].open(this.map, this.markers[index]);
      }
    },
    openInfowindow2(index) {
      this.infowindows[index].open(this.map, this.markers[index]);
    },
    closeInfowindow(index) {
      if (this.infowindows && this.infowindows[index] && this.openedInfowindowIndex != index) {
        this.infowindows[index].close();
      }
    },
    closeInfowindow2(index) {
      if (this.infowindows && this.infowindows[index]) {
        this.infowindows[index].close();
      }
    },
    setCenter(index) {
      // 이동할 위도 경도 위치
      var moveLatLon = this.positions[index].latlng;
      // 지도 중심을 이동 시킵니다
      // this.map.setCenter(moveLatLon);

      // 지도 중심을 부드럽게 이동시킵니다
      // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
      this.map.panTo(moveLatLon);
    },
    clickDetail(index, aptCode) {
      this.setCenter(index);
      this.openedInfowindowIndex = index;
      this.$emit("map-called", aptCode);
    },
    clickDetailFromSuper(index) {
      this.setCenter(index);
      this.openedInfowindowIndex = index;
    },
    btnSearch: function () {
      this.SET_HOUSE_MOVE_PAGE(1);
      this.$emit("house-list-called");
      var offsetTop =
        window.pageYOffset +
        document.querySelector("#btnSearchDiv").getBoundingClientRect().top -
        80;
      window.scrollTo({ top: offsetTop, left: 0, behavior: "smooth" });
    },
    btnDetailSearchOpen() {
      var div = document.querySelector("#detailSearch");
      if (this.isDetailSearchOpen) {
        this.isDetailSearchOpen = false;
        this.detailSearchOpenClose = "열기";
        div.className += "when-closed";
      } else {
        div.classList.remove("when-closed");
        var divMap = document.querySelector("#map");
        if (div.offsetTop + div.offsetHeight > divMap.offsetTop + divMap.offsetHeight) {
          div.style.top = divMap.offsetTop + divMap.offsetHeight - div.offsetHeight + "px";
        }
        this.isDetailSearchOpen = true;
        this.detailSearchOpenClose = "닫기";
      }
    },
    btnDetailOpen() {
      var div = document.querySelector("#detail");
      if (this.isDetailOpen) {
        this.isDetailOpen = false;
        this.detailOpenClose = "열기";
        div.className += "when-closed";
      } else {
        div.classList.remove("when-closed");
        var divMap = document.querySelector("#map");
        if (div.offsetTop + div.offsetHeight > divMap.offsetTop + divMap.offsetHeight) {
          div.style.top = divMap.offsetTop + divMap.offsetHeight - div.offsetHeight + "px";
        }
        this.isDetailOpen = true;
        this.detailOpenClose = "닫기";
      }
    },
    btnListOpen() {
      var div = document.querySelector("#list");
      if (this.isListOpen) {
        this.isListOpen = false;
        this.listOpenClose = "열기";
        div.className += "when-closed";
      } else {
        div.classList.remove("when-closed");
        var divMap = document.querySelector("#map");
        if (div.offsetTop + div.offsetHeight > divMap.offsetTop + divMap.offsetHeight) {
          div.style.top = divMap.offsetTop + divMap.offsetHeight - div.offsetHeight + "px";
        }
        this.isListOpen = true;
        this.listOpenClose = "닫기";
      }
    },
    btnDetailUp() {
      document.getElementById("detailScroll").scrollTop = 0;
    },
    btnBookmark(index, bookmarked) {
      if (this.userId == "" || this.userId == undefined) {
        this.$alertify.error("로그인이 필요한 서비스입니다.");
      } else {
        if (bookmarked == "F") {
          this.addBookmark(index);
        } else if (bookmarked == "T") {
          this.removeBookmark(index);
        }
      }
    },
    async addBookmark(index) {
      let bookmarkObj = {
        userId: this.userId,
        aptCode: this.houseList[index].aptCode,
      };
      try {
        let { data } = await http.post("/bookmark/apt", bookmarkObj);
        console.log(data);
        this.$alertify.success("북마크가 추가되었습니다.");
        this.$store.state.searchStore.houseList[index].bookmarked = "T";
        // document.getElementById("bookmark-" + index).src = "@/assets/T_star.png";
        this.CHANGE_BOOKMARKED();
      } catch (error) {
        console.error(error);
        this.$alertify.error("북마크를 추가하는 과정에 문제가 생겼습니다.");
      }
    },
    async removeBookmark(index) {
      try {
        let { data } = await http.delete(
          `/bookmark/apt/${this.userId}/${this.houseList[index].aptCode}`
        );
        console.log(data);
        this.$alertify.success("북마크가 삭제되었습니다.");
        this.$store.state.searchStore.houseList[index].bookmarked = "F";
        // document.getElementById("bookmark-" + index).src = "@/assets/T_star.png";
        this.CHANGE_BOOKMARKED();
      } catch (error) {
        console.error(error);
        this.$alertify.error("북마크를 삭제하는 과정에 문제가 생겼습니다.");
      }
    },
    movePage(pageIndex) {
      this.$emit("page-called", pageIndex);
    },
    detailSearchBookmark() {
      if (this.userId == "" || this.userId == undefined) {
        this.$alertify.error("로그인이 필요한 서비스입니다.");
      } else {
        this.$emit("bookmark-called");
      }
    },
    rollbackDetail() {
      this.SET_LOWER_VAL_PRICE(0);
      this.SET_UPPER_VAL_PRICE(150000);
    },
    makeSearchInfo: function () {
      var sidoName =
        document.getElementById("sido").options[document.getElementById("sido").selectedIndex].text;
      var gugunName =
        document.getElementById("gugun").options[document.getElementById("gugun").selectedIndex]
          .text;
      var dongName =
        document.getElementById("dong").options[document.getElementById("dong").selectedIndex].text;
      var searchInfoText = '"';
      if (this.selectSido != "sidoAll") {
        searchInfoText += sidoName + " ";
      }
      if (this.selectGugun != "gugunAll") {
        searchInfoText += gugunName + " ";
      }
      if (this.selectDong != "dongAll") {
        searchInfoText += dongName + " ";
      }
      searchInfoText += '" | "' + this.searchAptName + '"의 검색 결과';

      this.SET_SEARCH_INFO(searchInfoText);
    },
    myHouse() {
      if (this.userId == "" || this.userId == undefined) {
        this.$alertify.error("로그인이 필요한 서비스입니다.");
      } else {
        this.$emit("myhouse-called");
      }
    },
    upper: function () {
      var lowerVal = parseInt(this.lowerValPrice);
      var upperVal = parseInt(this.upperValPrice);

      if (upperVal < lowerVal + 100) {
        this.SET_LOWER_VAL_PRICE(upperVal - 100);
        if (lowerVal < 100) {
          this.SET_LOWER_VAL_PRICE(0);
          this.SET_UPPER_VAL_PRICE(100);
        }
      }
    },
    lower: function () {
      var lowerVal = parseInt(this.lowerValPrice);
      var upperVal = parseInt(this.upperValPrice);

      if (lowerVal > upperVal - 100) {
        this.SET_UPPER_VAL_PRICE(lowerVal + 100);
        if (upperVal > 150000 - 100) {
          this.SET_UPPER_VAL_PRICE(150000);
          this.SET_LOWER_VAL_PRICE(150000 - 100);
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
#wrapper {
  position: relative;
  /* max-width: 2200px !important; 1234 */
}

#list {
  resize: vertical;
  position: absolute;
  left: 7px;
  top: 103px;

  width: 380px;
  height: 81%;
  z-index: 3;

  /* background-color: lightblue;
            opacity: 0.8; */

  /* text color 은 안바뀌고 배경만 투명하게!! */
  background-color: rgba(255, 244, 244, 0.2);

  border: 1px solid lightgray;
  border-radius: 0.5rem;

  overflow: hidden;
}

#list > div {
  border-radius: 0.5rem;
}

#detail .scrolling {
  height: 91%;
  overflow-y: scroll;
}

#detailSearch .scrolling {
  height: 85%;
  margin: 0px !important;
  overflow-y: scroll;
  opacity: 0.93;
}

#detailSearch .scrolling > div {
  margin: 0px 0px 10px;
}

#detailSearch .scrolling > div:first-child {
  margin: 0px;
}

#list .scrolling {
  height: 93%;
  overflow-y: scroll;
}

#detailSearch {
  resize: vertical;
  position: absolute;
  top: 7px;
  right: 7px;

  width: 370px;
  height: 33%;
  z-index: 4;

  /* background-color: lightblue;
            opacity: 0.8; */

  /* text color 은 안바뀌고 배경만 투명하게!! */
  background-color: rgba(255, 244, 244, 0.2);

  overflow-y: scroll;

  border: 1px solid lightgray;
  border-radius: 0.5rem;

  overflow: hidden;
}

#searchDiv {
  position: absolute;
  top: 5px;
  left: 5px;
  width: 70%;
  height: 80px;
  z-index: 1;
}

#detailSearch > div {
  border-radius: 0.5rem;
}

#detail {
  resize: vertical;
  position: absolute;
  right: 7px;
  top: 290px;

  width: 370px;
  height: 58%;
  z-index: 5;

  /* background-color: lightblue;
            opacity: 0.8; */

  /* text color 은 안바뀌고 배경만 투명하게!! */
  background-color: rgba(255, 244, 244, 0.2);

  border: 1px solid lightgray;
  border-radius: 0.5rem;

  overflow: hidden;
}

.content-middle {
  z-index: 10;
  position: absolute;
}
.content-middle > div {
  margin: 0 !important;
  padding: 0px 20px !important;
}
.content-middle-info {
  left: 140px;
  bottom: 1px;
  height: 40px;
  line-height: 40px;
  margin: 7px;
  padding: 0px 18px 0px 15px;
  border-radius: 0.5rem;
  background-color: rgb(221, 221, 221, 0.8);
  box-shadow: 1px 1px 1px 1px rgba(105, 105, 105, 0.8);
}
.content-middle-pagination {
  right: 20px;
  bottom: 5px;
}

#detail > div {
  border-radius: 0.5rem;
}

/* width */
::-webkit-scrollbar {
  width: 5px;
}

/* Track */
::-webkit-scrollbar-track {
  background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: #888;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.list-group-item {
  margin-top: 2px;
  margin-bottom: 3px;
  border-radius: 0.5rem;
  opacity: 0.95;
  box-shadow: 1px 1px 1px 1px rgba(105, 105, 105, 0.8);
}

#list .list-group-item {
  cursor: pointer;
}

#list .list-group-item:hover {
  background-color: #ebfceb;
}

.house-detail {
  background-color: white;
  margin-top: 2px;
  margin-bottom: 3px;
  height: 200px;
  border-radius: 0.5rem;
  opacity: 0.93;
  box-shadow: 1px 1px 1px 1px rgba(105, 105, 105, 0.8);
}

.moving-title {
  background: #dadada;
  margin-bottom: 3px;
  box-shadow: 1px 1px 1px 1px rgba(105, 105, 105, 0.8);
  font-weight: 600;
}

.none-text-drag {
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -khtml-user-select: none;
  -webkit-user-select: none;
  user-select: none;
}

.open-close {
  position: relative;
  float: right;
  padding-left: 8px;
  padding-right: 8px;
  padding-bottom: 8px;
  cursor: pointer !important;
}

.when-closed {
  height: 44px !important;
  opacity: 0.85;
}

.s1 {
  width: 210px;
  font-weight: 600;
}
.s2 {
  width: 110px;
}
.s3 {
  width: 27px;
  height: 27px;
}
.s3 img {
  height: 27px;
  width: 27px;
  margin-bottom: 1px;
}

.c1 {
  width: 78px;
}
.c2 {
  width: 90px;
}
.c3 {
  width: 70px;
}
.c4 {
  width: 38px;
}

.display-none {
  display: none;
}

.scrolling li {
  background-color: rgba(248, 248, 248, 0.98);
}

.detail-table {
  text-align: left;
  line-height: 20px;
  border-collapse: separate;
  border-spacing: 0;
  border: 2px solid #ed1c40;
  width: 100%;
  height: 200px;
  margin: auto;
  border-radius: 0.25rem;
}

.detail-table thead tr:first-child {
  background: rgb(237, 28, 64, 0.8);
  color: #fff;
  border: none;
}

.detail-table th:first-child,
.detail-table td:first-child {
  padding: 0 10px 0 10px;
}

.detail-table th {
  font-weight: 500;
}

.detail-table thead {
  height: 40px;
}

.detail-table thead tr:last-child th {
  border-bottom: 3px solid #ddd;
}

.detail-table tbody tr:hover {
  background-color: #f2f2f2;
  cursor: default;
}

.detail-table tbody tr:last-child td {
  border: none;
}

.detail-table tbody td {
  border-bottom: 1px solid #ddd;
}

.detail-table td:last-child {
  text-align: right;
  padding-right: 10px;
}

.bookmark-div {
  float: right;
  width: 30px;
  height: 30px !important;
  background-color: #26a3ff;
  border-radius: 5px;
  cursor: pointer;
}

.bookmark-div-detail {
  float: left;
}

.bookmark-div img {
  height: 30px;
  width: auto;
}

.detail-search-div {
  padding: 10px;
  background-color: rgba(248, 248, 248, 0.98);
  width: 360px;
}

.detail-search-div > div > * {
  margin: 5px;
}

input[type="range"] {
  box-sizing: border-box;
  appearance: none;
  width: 330px;
  margin: 0;
  padding: 0 2px;
  /* Add some L/R padding to ensure box shadow of handle is shown */
  overflow: hidden;
  border: 0;
  border-radius: 1px;
  outline: none;
  background: linear-gradient(grey, grey) no-repeat center;
  /* Use a linear gradient to generate only the 2px height background */
  background-size: 100% 2px;
  pointer-events: none;
}
input[type="range"]:active,
input[type="range"]:focus {
  outline: none;
}

input[type="range"]::-webkit-slider-thumb {
  height: 28px;
  width: 28px;
  border-radius: 28px;
  background-color: #fff;
  position: relative;
  margin: 5px 0;
  /* Add some margin to ensure box shadow is shown */
  cursor: pointer;
  appearance: none;
  pointer-events: all;
  box-shadow: 0 1px 4px 0.5px rgba(0, 0, 0, 0.25);
}
input[type="range"]::-webkit-slider-thumb::before {
  content: " ";
  display: block;
  position: absolute;
  top: 13px;
  left: 100%;
  width: 360px;
  height: 2px;
}

.multi-range {
  position: relative;
  height: 50px;
}
.multi-range input[type="range"] {
  position: absolute;
}

.multi-range input[type="range"]:nth-child(1)::-webkit-slider-thumb::before {
  background-color: red;
}

.multi-range input[type="range"]:nth-child(2) {
  background: none;
}

.multi-range input[type="range"]:nth-child(2)::-webkit-slider-thumb::before {
  background-color: grey;
}

#search-form {
  padding: 0px !important;
  opacity: 0.93;
  box-shadow: 0 1px 4px 0.5px rgba(0, 0, 0, 0.25);
}

#search-form select {
  padding-left: 30px !important;
}

.col-search-1 {
  width: 22%;
}
.col-search-2 {
  width: 22%;
}
.col-search-3 {
  width: 22%;
}
.col-search-4 {
  width: 22%;
}
.col-search-5 {
  width: 12%;
}
</style>
