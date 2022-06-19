import http from "@/common/axios.js";
import util from "@/common/util.js";
import router from "@/routers/routers.js";

export default {
  // namespaced: true,
  state: {
    // list
    list: [],
    limit: 10,
    offset: 0,
    searchWord: "",
    boardClsf:"",

      // pagination
      listRowCount: 10,
      pageLinkCount: 10,
      currentPageIndex: 1,

      totalListItemCount: 0,

      // detail, update, delete

      boardId: 0,
      boardTitle: "",
      boardContent: "",
      userName: "",
      boardRegDate: "",
      boardRegTime: "",
      boardReadCount: 0,
      fileList: [],
      sameUser: false,
  },
  mutations: {
    SET_BOARD_SEARCH_WORD(state, searchWord) {
      state.searchWord = searchWord;
    },
    SET_BOARD_LIST(state, list) {
      state.list = list;
    },

    SET_BOARD_TOTAL_LIST_ITEM_COUNT(state, count) {
      state.totalListItemCount = count;
    },
    SET_BOARD_MOVE_PAGE(state, pageIndex) {
      state.offset = (pageIndex - 1) * state.listRowCount;
      state.currentPageIndex = pageIndex;
    },
    SET_BOARD_DETAIL(state, payload) {
      state.boardId = payload.boardId;
      state.boardTitle = payload.boardTitle;
      state.boardContent = payload.boardContent;
      state.userName = payload.userName;
      state.boardRegDate = util.makeDateStr(
        payload.boardRegDt.date.year,
        payload.boardRegDt.date.month,
        payload.boardRegDt.date.day,
        "."
      );
      state.boardRegTime = util.makeTimeStr(
        payload.boardRegDt.time.hour,
        payload.boardRegDt.time.minute,
        payload.boardRegDt.time.second,
        ":"
      );
      state.boardReadCount = payload.boardReadCount;
      state.fileList = payload.fileList;
      state.sameUser = payload.sameUser;
    },
    // for UpdateModal title v-modal
    SET_BOARD_TITLE(state, title) {
      state.boardTitle = title;
    },
  },
  actions: {
    async boardList(context,bc) {
      let params = {
        boardClsf: bc,
        limit: this.getters.getBoardLimit,
        offset: this.getters.getBoardOffset,
        searchWord: this.getters.getBoardSearchWord,

      };

      try {
        let { data } = await http.get("/boards", { params }); // params: params shorthand property, let response 도 제거
        // console.log("BoardMainVue: data : ");
        // console.log(data);
        if (data.result == "login") {
          router.push("/login");
        } else {
          context.commit("SET_BOARD_LIST", data.list);
          context.commit("SET_BOARD_TOTAL_LIST_ITEM_COUNT", data.count);
        }
      } catch (error) {
        console.error(error);
      }
    },
  },
  getters: {
    getBoardId(state)
    {
      return state.boardId;
    },
    getBoardLimit(state)
    {
      return state.limit;
    },
    getBoardOffset(state)
    {
      return state.offset;
    },
    getBoardSearchWord(state)
    {
      return state.searchWord;
    },
    getBoardList: function (state) {
      return state.list;
    },
    // pagination
    getPageCount: function (state) {
      return Math.ceil(state.totalListItemCount / state.listRowCount);
    },
    getStartPageIndex: function (state) {
      if (state.currentPageIndex % state.pageLinkCount == 0) {
        //10, 20...맨마지막
        return (state.currentPageIndex / state.pageLinkCount - 1) * state.pageLinkCount + 1;
      } else {
        return Math.floor(state.currentPageIndex / state.pageLinkCount) * state.pageLinkCount + 1;
      }
    },
    getEndPageIndex: function (state, getters) {
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
      return ret > getters.getPageCount ? getters.getPageCount : ret;
    },
    getPrev: function (state) {
      if (state.currentPageIndex <= state.pageLinkCount) {
        return false;
      } else {
        return true;
      }
    },
    getNext: function (state, getters) {
      if (
        Math.floor(getters.getPageCount / state.pageLinkCount) * state.pageLinkCount <
        state.currentPageIndex
      ) {
        return false;
      } else {
        return true;
      }
    },
  },
};
