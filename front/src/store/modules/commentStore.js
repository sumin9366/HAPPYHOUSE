import http from "@/common/axios.js";
import util from "@/common/util.js";
import router from "@/routers/routers.js";

export default {
  // namespaced: true,
  state: {
    // list
    list: [],
    limit: 5,
    offset: 0,

      // pagination
      listRowCount: 5,
      pageLinkCount: 5,
      currentPageIndex: 1,

      totalListItemCount: 0,

      // detail, update, delete

      boardId: 0,
      commentId: 0,
      commentContent: "",
      userName: "",
      userId: "",
      commentRegDate: "",
      commentRegTime: "",
      sameUser: false,
  },
  mutations: {
    SET_COMMENT_LIST(state, list) {
      state.list = list;
    },

    SET_COMMENT_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.totalListItemCount = count;
    },
    SET_COMMENT_MOVE_PAGE(state, pageIndex) {
      state.offset = (pageIndex - 1) * state.listRowCount;
      state.currentPageIndex = pageIndex;
    },
    SET_COMMENT_DETAIL(state, payload) {
      state.boardId = payload.boardId;
      state.commentId = payload.commentId;
      state.commentContent = payload.commentContent;
      state.userName = payload.userId;
      // 나중에 네임 id 확인해야함 join 해서 name 가져오던지 
      state.sameUser = payload.sameUser;
      
      state.commentRegDate = util.makeDateStr(
        payload.commentRegDt.date.year,
        payload.commentRegDt.date.month,
        payload.commentRegDt.date.day,
        "."
      );
      state.commentRegTime = util.makeTimeStr(
        payload.commentRegDt.time.hour,
        payload.commentRegDt.time.minute,
        payload.commentRegDt.time.second,
        ":"
      );
    },
  },
  actions: {
    async commentList(context,boardIdPara) {
      
      let params = {
        //보드 아이디 넘겨줘야함
       
        boardId: boardIdPara,
        limit: this.getters.getCommentLimit,
        offset: this.getters.getCommentOffset,

      };
      
      try {
        console.log()
        console.log("comments params : ");
        console.log(params);
        let { data } = await http.get("/comments", { params }); // params: params shorthand property, let response 도 제거
        console.log("comments data : ");
        console.log(data);
        if (data.result == "login") {
          router.push("/login");
        } else {
          context.commit("SET_COMMENT_LIST", data.list);
          context.commit("SET_COMMENT_TOTAL_LIST_ITEM_COUNT", data.count);
        }
      } catch (error) {
        console.error(error);
      }
    },
  },
  getters: {
    getCommentboardId(state)
    {
      return state.boardId;
    },
    getCommentLimit(state)
    {
      return state.limit;
    },
    getCommentOffset(state)
    {
      return state.offset;
    },
    getCommentList: function (state) {
      return state.list;
    },
    // pagination
    // getPageCount: function (state) {
    //   return Math.ceil(state.totalListItemCount / state.listRowCount);
    // },
    // getStartPageIndex: function (state) {
    //   if (state.currentPageIndex % state.pageLinkCount == 0) {
    //     //10, 20...맨마지막
    //     return (state.currentPageIndex / state.pageLinkCount - 1) * state.pageLinkCount + 1;
    //   } else {
    //     return Math.floor(state.currentPageIndex / state.pageLinkCount) * state.pageLinkCount + 1;
    //   }
    // },
    // getEndPageIndex: function (state, getters) {
    //   let ret = 0;
    //   if (state.currentPageIndex % state.pageLinkCount == 0) {
    //     //10, 20...맨마지막
    //     ret =
    //       (state.currentPageIndex / state.pageLinkCount - 1) * state.pageLinkCount +
    //       state.pageLinkCount;
    //   } else {
    //     ret =
    //       Math.floor(state.currentPageIndex / state.pageLinkCount) * state.pageLinkCount +
    //       state.pageLinkCount;
    //   }
    //   // 위 오류나는 코드를 아래와 같이 비교해서 처리
    //   return ret > getters.getPageCount ? getters.getPageCount : ret;
    // },
    // getPrev: function (state) {
    //   if (state.currentPageIndex <= state.pageLinkCount) {
    //     return false;
    //   } else {
    //     return true;
    //   }
    // },
    // getNext: function (state, getters) {
    //   if (
    //     Math.floor(getters.getPageCount / state.pageLinkCount) * state.pageLinkCount <
    //     state.currentPageIndex
    //   ) {
    //     return false;
    //   } else {
    //     return true;
    //   }
    // },
  },
};
