<template>
  <div class="modal" tabindex="-1" id="detailModal">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">글 상세</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <div class="modal-body">
          <table class="table table-hover">
            <tbody>
              <tr>
                <td>글번호</td>
                <td>{{ $store.state.boardStore.boardId }}</td>
              </tr>
              <tr>
                <td>제목</td>
                <td>{{ $store.state.boardStore.boardTitle }}</td>
              </tr>
              <tr>
                <td>내용</td>
                <td v-html="$store.state.boardStore.boardContent"></td>
              </tr>
              <tr>
                <td>작성자</td>
                <td>{{ $store.state.boardStore.userName }}</td>
              </tr>
              <!-- 아래 코드는 오류 발생 초기 생성 시점에 regDt = {} -->
              <!-- <tr><td>작성일시</td><td>{{ makeDateStr(regDt.date.year, regDt.date.month, regDt.date.day, '.') }}</td></tr> -->
              <tr>
                <td>작성일시</td>
                <td>
                  {{ $store.state.boardStore.boardRegDate }}
                  {{ $store.state.boardStore.boardRegTime }}
                </td>
              </tr>
              <tr>
                <td>조회수</td>
                <td>{{ $store.state.boardStore.boardReadCount }}</td>
              </tr>
              <!-- New for FileUpload -->
              <tr>
                <td colspan="2">첨부파일</td>
              </tr>
              <tr v-if="$store.state.boardStore.fileList.length > 0">
                <td colspan="2">
                  <div v-for="(file, index) in $store.state.boardStore.fileList" :key="index">
                    <span class="fileName"
                      >{{ file.fileName }} {{ $store.state.boardStore.sameUser }}</span
                    >

                    <a
                      type="button"
                      class="btn btn-outline btn-default btn-xs"
                      v-bind:href="file.fileUrl"
                      v-bind:download="file.fileName"
                      >내려받기</a
                    >
                  </div>
                </td>
              </tr>
              <!-- / New for FileUpload -->
            </tbody>
          </table>
        </div>
        <div class="modal-footer">
          <button
            v-if="$store.state.boardStore.sameUser"
            @click="changeToUpdate"
            class="btn btn-sm btn-primary btn-outline"
            data-dismiss="modal"
            type="button"
          >
            글 수정하기
          </button>
           <button
            v-else-if="this.$store.state.loginStore.userClsf=='001'"
            @click="changeToUpdate"
            class="btn btn-sm btn-primary btn-outline"
            data-dismiss="modal"
            type="button"
          >
            글 수정하기
          </button>
          <button
            v-if="$store.state.boardStore.sameUser"
            @click="changeToDelete"
            class="btn btn-sm btn-warning btn-outline"
            data-dismiss="modal"
            type="button"
          >
            글 삭제하기
          </button>
          <button
            v-else-if="this.$store.state.loginStore.userClsf=='001'"
            @click="changeToDelete"
            class="btn btn-sm btn-warning btn-outline"
            data-dismiss="modal"
            type="button"
          >
            글 삭제하기
          </button>
          <!-- 댓글부분 -->

          <table class="table table-hover">
            <thead>
              <tr>
                <th>작성자</th>
                <th>내용</th>
                <th>작성일시</th>
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
                v-for="(comment, index) in commentListGetters"
                @click="commentAlertDelete(comment.commentId, comment.userId)"
                v-bind:key="index"
              >
                <td>{{ comment.userId }}</td>
                <td>{{ comment.commentContent }}</td>
                <td>{{ comment.commentRegDt.date | makeDateStr(".") }}</td>
              </tr>
            </tbody>
          </table>
          <div class="input-group mb-3">
            <!-- store 사용 -->
            <!-- <input v-model="searchWord" @keydown.enter="boardList" type="text" class="form-control"> -->

            <!-- $store.state에 직접 접근하여 양방향 바인딩 -->
            <input
              v-model="$store.state.commentStore.commentContent"
              type="text"
              class="form-control"
            />
            <button @click="commentInsert" class="btn btn-success" type="button">댓글쓰기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// export default {
//     methods: {
//   changeToUpdate() {
//       this.$emit("call-parent-change-to-update");
//     },
//     changeToDelete() {
//       this.$emit("call-parent-change-to-delete");
//     },
//     }
// }
// import { Modal } from "bootstrap";

import http from "@/common/axios.js";
import util from "@/common/util.js";

// 삭제
import Vue from "vue";
import VueAlertify from "vue-alertify";
Vue.use(VueAlertify);

export default {
  data() {
    return {};
  },
  computed: {
    // gttters 이용
    // $store.getters.메소드이름 을 통해 store의 getters에 있는 메소드를 호출하여 state의 데이터를 가져옴
    commentListGetters() {
      // console.log(this.$store.getters.getCommentList);
      return this.$store.getters.getCommentList; // no getBoardList()
    },
  },

  methods: {
    showController()
    {
      if(this.$store.state.loginStore.userClsf=='001')
        return true;
      else
        return this.$store.state.boardStore.sameUser;
    },
    changeToUpdate() {
      this.$emit("call-parent-change-to-update");
    },
    changeToDelete() {
      this.$emit("call-parent-change-to-delete");
    },
    commentAlertDelete(commentId, userId) {
      if (this.$store.state.loginStore.userId == userId ||
      this.$store.state.loginStore.userClsf=='001') {
        this.$store.state.commentStore.commentId = commentId;
        var $this = this;
        this.$alertify.confirmWithTitle(
          "삭제 확인",
          "이 댓글을 삭제하시겠습니까?",
          function () {
            try {
              $this.commentDelete();
              // http.delete("/comments/" + commentId);
              // $this.$alertify.success("댓글이 삭제되었습니다.");
            } catch (error) {
              console.log("BoardMainVue: error : ");
              console.log(error);
            }

            // $this.commentDelete(commentId); // $this 사용
          },
          function () {
            console.log("cancel");
          }
        );
        // this.commentList();
      } else {
        this.$alertify.success("직접 작성한 댓글이 아닙니다.");
      }
    },
    async commentDelete() {
      // parameter 사용 X

      try {
        await http.delete("/comments/" + this.$store.state.commentStore.commentId);
        this.$alertify.success("댓글이 삭제되었습니다.");
        await this.commentList();
      } catch (error) {
        console.log("comment: error : ");
        console.log(error);
      }
    },

    async commentInsert() {
      let formData = new FormData();
      formData.append("commentContent", this.$store.state.commentStore.commentContent);
      formData.append("boardId", this.$store.state.boardStore.boardId);
      formData.append("userId", this.$store.state.loginStore.userId);

      let options = {
        headers: { "Content-Type": "multipart/form-data" },
      };

      try {
        console.log(formData);
        let { data } = await http.post("/comments", formData, options);

        console.log("InsertModalVue: data : ");
        console.log(data);
        if (data.result == "login") {
          this.doLogout();
        } else {
          this.$store.state.commentStore.commentContent = "";
          this.$alertify.success("댓글이 등록되었습니다.");
          this.commentList();
        }
      } catch (error) {
        console.log("InsertModalVue: error ");
        console.log(error);
      }
    },

    // list
    // store actions 에 구현
    // 가능한 한 가지 방법
    // $store.dispatch("메소드 이름")을 통해 store의 actions에 있는 메소드를 호출함 (async function 등)
    commentList() {
      // console.log("commentList실행");
      // console.log(this.$store.state.loginStore.userId);
      this.$store.dispatch("commentList", this.$store.getters.getBoardId);
    },

    // pagination
    // movePage(pageIndex) {
    //   console.log("CommentVue : movePage : pageIndex : " + pageIndex);

    //   // store commit 으로 변경
    //   // this.offset = (pageIndex - 1) * this.listRowCount;
    //   // this.currentPageIndex = pageIndex;
    //   // $store.commit("SET_BOARD_MOVE_PAGE", pageIndex)를 호출하여
    //   // mutations의 SET_BOARD_MOVE_PAGE(state, pageIndex)가 호출되고 state값이 변경됨
    //   this.$store.commit("SET_COMMENT_MOVE_PAGE", pageIndex);

    //   this.commentList();
    // },

    // util
    makeDateStr: util.makeDateStr,

    closeAfterUpdate() {
      // this.updateModal.hide();
      this.commentList();
    },

    doLogout() {
      this.$store.commit("SET_LOGIN", { isLogin: false, userName: "", userProfileImageUrl: "" });
      this.$router.push("/login");
    },
  },
  created() {
    this.commentList();
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
