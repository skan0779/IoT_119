import { createStore } from 'vuex';

const store = createStore({
  state() {
    return {
      resultData: null,  // 서버에서 받은 데이터를 저장하는 상태
      historyData: []
    };
  },
  mutations: {
    setResultData(state, result) {
      state.resultData = result;
    },
    addHistoryData(state, data) {
      // 중복된 고객 이름이 있는지 확인
      const isDuplicate = state.historyData.some(
        item => item.customer.name === data.customer.name
      );
      if (!isDuplicate) {
        state.historyData.push(data);
      }
    }
  },
  actions: {
    saveResultData({ commit }, result) {
      commit('setResultData', result);  // 데이터를 저장하는 액션
    },
    saveHistoryData({ commit }, data) {
      commit('addHistoryData', data);
    }
  },
  getters: {
    getResultData(state) {
      return state.resultData;
    },
    getHistoryData: (state) => state.historyData
  }
});

export default store;
