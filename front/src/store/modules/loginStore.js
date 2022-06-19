export default {
  namespaced: true,
  state: {
    // NavBar
    isLogin: false,

    userName: "",

    // Login
    userId: "",
    userPw: "",
    userAddress: "",
    userPhone: "",
    userEmail: "",
    userClsf: "",
  },
  mutations: {
    SET_LOGIN(state, payload) {
      state.isLogin = payload.isLogin;
      state.userName = payload.userName;
      state.userId = payload.userId;
      state.userPw = payload.userPw;
      state.userAddress = payload.userAddress;
      state.userPhone = payload.userPhone;
      state.userEmail = payload.userEmail;
      state.userClsf = payload.userClsf;
    },
  },
  actions: {},
  getters: {
    isLogin: function (state) {
      return state.isLogin;
    },
    userId: function (state) {
      return state.userId;
    },
  },
};
