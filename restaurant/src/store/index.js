import { createStore } from "vuex";
import router from "@/router/index";

const state = {};
const getters = {};
const mutations = {
  redirectTo(state, payload) { // Added `payload` parameter
    router.push({ name: payload }); // Use `payload` directly
  },
};
const actions = {
  redirectTo({ commit }, payload) {
    commit("redirectTo", payload.val); // Ensure `payload.val` matches your action input
  },
};
export default createStore({
  state,
  getters,
  mutations,
  actions,
  modules: {},
});

