<script>
import { mapGetters } from 'vuex';

export default {
  computed: {
    // Vuex에서 historyData 가져오기 << ChargePage에서 생성
    ...mapGetters(['getHistoryData']),
  }
}
</script>

<template>
  <div>
    <main>
      <!-- 이력조회 항목 -->
      <section class="content">
        <div class="main-content">
          <div class="main_text">
            <h2>요금제 조회 내역</h2>
            <div v-if="getHistoryData.length > 0" class="history-list">
              <div v-for="(item, index) in getHistoryData" :key="index" class="history-item">
                <p>고객 ID: {{ item.customer.name }}</p>
                <p>고객 업종: {{ item.customer.category }}</p>
                <p>추천 요금제: {{ item.plan.recommendName }}</p>
                <p>기본 요금(월): {{ item.plan.recommendFee }}</p>
                <p>기본 데이터: {{ item.plan.recommendData }}</p>
              </div>
            </div>
            <div v-else>
              <p>저장된 추천 내역이 없습니다.</p>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
.history-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.history-list {
  margin-top: 20px;
}

.history-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #f9f9f9;
  padding: 15px 20px;
  margin-bottom: 15px;
  text-align: center;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.history-item p {
  font-size: 16px;
}

main {
  padding-top: 45px;
}

.content {
  padding: 40px 20px;
}

.main-content {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  max-width: 900px;
  margin: 0 auto;
  background-color: rgba(220, 220, 220, 0.429);
  padding: 40px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.main_text {
  flex: 1;
  margin-right: 50px;
}

.main_text h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 15px;
}

.main_text p {
  font-size: 14px;
  font-weight: 500;
  color: #555;
  margin-bottom: 25px;
}

.main_text label {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 5px;
  display: block;
}
</style>