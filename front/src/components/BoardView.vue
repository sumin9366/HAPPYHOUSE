<template>
  <div>
    <div class="page-heading">
      <div class="container">
        <div class="row">
          <div class="col-lg-8">
            <div class="top-text header-text">
              <h6>&nbsp;</h6>
              <h2>자유 게시판</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="contact-page mt-2">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="inner-content">
              <!-- 여기서 -->
              <div class="container">
                <h4 class="text-center">자유 게시판</h4>

                <div class="input-group mb-3">
                  <!-- store 사용 -->
                  <!-- <input v-model="searchWord" @keydown.enter="boardList" type="text" class="form-control"> -->

                  <!-- $store.state에 직접 접근하여 양방향 바인딩 -->
                  <input
                    v-model="$store.state.boardStore.searchWord"
                    @keydown.enter="boardList"
                    type="text"
                    class="form-control"
                  />
                  <button @click="searchButton" class="btn btn-success" type="button">Search</button>
                </div>

                <table class="table table-hover">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>제목</th>
                      <th>작성자</th>
                      <th>작성일시</th>
                      <th>조회수</th>
                    </tr>
                  </thead>
                  <tbody>
                    <!-- store 사용 -->

                    <!-- 직접 store 에 접근해도 된다. -->
                    <!-- <tr v-for="(board, index) in $store.state.board.list" @click="boardDetail(board.boardId)" v-bind:key="index"> -->

                    <!-- getters 를 이용하는 코드 -->
                    <!-- computed - listGetters - getBoardList  -->

                    <!-- computed에서 선언한 getter 역할의 listGetters 함수의 이름을 이용하여 store.js의 getters를 통해 state 데이터를 가져옴 -->
                    <tr
                      style="cursor: pointer"
                      v-for="(board, index) in listGetters"
                      @click="boardDetail(board.boardId)"
                      v-bind:key="index"
                    >
                      <td>{{ board.boardId }}</td>
                      <td>{{ board.boardTitle }}</td>
                      <td>{{ board.userName }}</td>
                      <td>{{ board.boardRegDt.date | makeDateStr(".") }}</td>
                      <td>{{ board.boardReadCount }}</td>
                    </tr>
                  </tbody>
                </table>

                <pagination-view v-on:call-parent="movePage"></pagination-view>

                 <button class="btn btn-sm btn-primary mb-3" @click="showInsertModal">글쓰기</button>

                <insert-modal v-on:call-parent-insert="closeAfterInsert"></insert-modal>
                <!-- props 제거 -->
                <detail-modal
                  v-on:call-parent-change-to-update="changeToUpdate"
                  v-on:call-parent-change-to-delete="changeToDelete"
                ></detail-modal>
                <update-modal v-on:call-parent-update="closeAfterUpdate"></update-modal>
              </div>

              <!-- 여기까지 -->
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InsertModal from "./modals/InsertModal.vue";
import DetailModal from "./modals/DetailModal.vue";
import UpdateModal from "./modals/UpdateModal.vue";

import { Modal } from "bootstrap";

import http from "@/common/axios.js";
import util from "@/common/util.js";

import PaginationView from "./PaginationView.vue";

// 삭제
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

export default {
  name: "BoardMain",
  components: { InsertModal, DetailModal, UpdateModal, PaginationView },
  data() {
    return {
      // modal
      insertModal: null,
      detailModal: null,
      updateModal: null,
    };
  },
  computed: {
    // gttters 이용
    // $store.getters.메소드이름 을 통해 store의 getters에 있는 메소드를 호출하여 state의 데이터를 가져옴
    listGetters() {
      // console.log("listGetters 여기들어옴?")
      return this.$store.getters.getBoardList; // no getBoardList()
    },
  },
  methods: {
    // list
    // store actions 에 구현
    // 가능한 한 가지 방법
    // $store.dispatch("메소드 이름")을 통해 store의 actions에 있는 메소드를 호출함 (async function 등)
    boardList() {
      //  console.log("이거머임"+this.$store.state.boardStore.limit);
      this.$store.state.boardStore.boardClsf = "002";
      this.$store.dispatch("boardList", "002"); //자유게시판은 002 던지고 공지사항은 001 던지면됨
    },
    searchButton()
    {
      this.$store.state.boardStore.offset=0;
      this.$store.state.boardStore.currentPageIndex=1;
      this.boardList();
      this.$store.state.boardStore.searchWord="";
    },
    // pagination
    movePage(pageIndex) {
      // console.log("BoardMainVue : movePage : pageIndex : " + pageIndex);

      // store commit 으로 변경
      // this.offset = (pageIndex - 1) * this.listRowCount;
      // this.currentPageIndex = pageIndex;
      // $store.commit("SET_BOARD_MOVE_PAGE", pageIndex)를 호출하여
      // mutations의 SET_BOARD_MOVE_PAGE(state, pageIndex)가 호출되고 state값이 변경됨
      this.$store.commit("SET_BOARD_MOVE_PAGE", pageIndex);

      this.boardList();
    },

    // util
    makeDateStr: util.makeDateStr,

    // insert
    showInsertModal() {
      this.insertModal.show();
    },

    closeAfterInsert() {
      this.insertModal.hide();
      this.boardList();
    },

    // detail
    async boardDetail(boardId) {
      // store 변경
      // this.boardId = boardId;
      // this.$store.commit('mutateSetBoardBoardId', boardId);

      try {
        let { data } = await http.get("/boards/" + boardId);
        // if(data.dto.userId == this.$store.state.loginStore.userId)
        // {
        //   this.$store.state.loginStore.sameUser=true;
        //   console.log(this.$store.state.loginStore.sameUser);
        // }
        //         if(this.$store.state.boardStore.userId)
        if (data.result == "login") {
          this.doLogout(); // this.$router.push("/login"); 에서 변경
        } else {
          let { dto } = data;
          this.$store.commit("SET_BOARD_DETAIL", dto);
          this.$store.dispatch("commentList", this.$store.getters.getBoardId);
          //  console.log(this.$store.state.loginStore.boardClsf);
          this.detailModal.show();
        }
      } catch (error) {
        console.log("BoardMainVue: error : ");
        console.log(error);
      }
    },

    changeToUpdate() {
      // board parameter 필요 없음.
      // data update 사용 X
      // this.board = board;
      this.detailModal.hide();
      this.updateModal.show();
    },

    closeAfterUpdate() {
      this.updateModal.hide();
      this.boardList();
    },

    // delete
    // $emit board 사용 X
    // changeToDelete(board){
    changeToDelete() {
      this.detailModal.hide();

      var $this = this; // alertify.confirm-function()에서 this 는 alertify 객체
      this.$alertify.confirmWithTitle(
        "삭제 확인",
        "이 글을 삭제하시겠습니까?",
        function () {
          // board.boardId 사용 X
          $this.boardDelete(); // $this 사용
        },
        function () {
          console.log("cancel");
        }
      );
    },
    async boardDelete() {
      // parameter 사용 X

      try {
        let { data } = await http.delete("/boards/" + this.$store.state.boardStore.boardId);
        // console.log(data);

        if (data.result == "login") {
          this.doLogout();
        } else {
          this.$alertify.success("글이 삭제되었습니다.");
          this.boardList();
        }
      } catch (error) {
        console.log("BoardMainVue: error : ");
        console.log(error);
      }
    },
    // logout 처리 별도 method
    doLogout() {
      this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
      this.$router.push("/login");
    },
  },
  created() {
    this.boardList();
  },
  mounted() {
    this.insertModal = new Modal(document.getElementById("insertModal"));
    this.detailModal = new Modal(document.getElementById("detailModal"));
    this.updateModal = new Modal(document.getElementById("updateModal"));
  },
  filters: {
    makeDateStr: function (date, separator) {
      return (
        date.year +
        separator +
        (date.month < 10 ? "0" + date.month : date.month) +
        separator +
        (date.day < 10 ? "0" + date.day : date.day)
      );
    },
  },
};
</script>

<style></style>
