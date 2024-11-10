<script>
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  scales
} from 'chart.js'
import { Bar } from 'vue-chartjs'
import { ref, onMounted } from 'vue';
import { mapActions } from 'vuex';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend)

export default {
  // Vuex에서 데이터 가져오기 << CustomerPage에서 생성
  computed: {
    getResultData() {
      return this.$store.getters.getResultData;
    }
  },
  name: 'App',
  components: {
    Bar
  },
  data() {
    return {
      // 1-1. 차트 데이터
      chartData: {
        labels: [],
        datasets: [
          {
            label: '일일 데이터 사용량(KB)',
            backgroundColor: '#42A5F5',
            data: []
          }
        ]
      },
      // 1-2. 차트 옵션
      chartOptions: {
        maintainAspectRatio: false,
        responsive: true
      },
      // 1-3. 차트 존재 여부
      chart: null,
      statistics: null,
      recommends: null
    };
  },
  methods: {
    // 1. Vuex 연결
    ...mapActions(['saveHistoryData']),
    // 2-1. 평균
    calculateMean(data) {
      const sum = data.reduce((acc, value) => acc + value, 0);
      return sum / data.length;
    },
    // 2-2. 모분산
    calculatePopulationVariance(data) {
      const mean = this.calculateMean(data);
      const squaredDiffs = data.map(value => {
        const diff = value - mean;
        return diff * diff;
      });
      const sumSquaredDiffs = squaredDiffs.reduce((acc, value) => acc + value, 0);
      return sumSquaredDiffs / data.length;
    },
    // 2-3. 표본분산
    calculateSampleVariance(data) {
      const mean = this.calculateMean(data);
      const squaredDiffs = data.map(value => {
        const diff = value - mean;
        return diff * diff;
      });
      const sumSquaredDiffs = squaredDiffs.reduce((acc, value) => acc + value, 0);
      return sumSquaredDiffs / (data.length - 1);
    },
    // 2-4. 표준편차
    calculateStandardDeviation(data, isSample = true) {
      const variance = isSample
        ? this.calculateSampleVariance(data)
        : this.calculatePopulationVariance(data);
      return Math.sqrt(variance);
    },
    // 2-5. 최대값
    calculateMax(data) {
      return Math.max(...data);
    },
    // 2-6. 최소값
    calculateMin(data) {
      return Math.min(...data);
    },
    // 2-7. 예상 사용량
    calculateExpectation(data) {
      const mean = this.calculateMean(data)
      return mean * 31
    },
    // 3. 표출 형식 (Kb -> MB)
    formatNumber(number) {
      return new Intl.NumberFormat().format(Math.floor(number / 1024));
    },
    // 4. 통계량 계산
    calculateStatistics() {
      const data = this.chartData.datasets[0].data;
      if (data.length === 0) return {};
      return {
        mean: this.formatNumber(this.calculateMean(data)),
        populationVariance: this.formatNumber(this.calculatePopulationVariance(data)),
        sampleVariance: this.formatNumber(this.calculateSampleVariance(data)),
        standardDeviation: this.formatNumber(this.calculateStandardDeviation(data)),
        max: this.formatNumber(this.calculateMax(data)),
        min: this.formatNumber(this.calculateMin(data)),
        expectation: this.formatNumber(this.calculateExpectation(data)),
      };
    },
    // 5. 요금제 계산
    recommendCharge() {
      const data = this.chartData.datasets[0].data;
      let charge_name = '';
      let charge_fee = '';
      let charge_data = '';
      // 한달 평균 총사용량
      const charge_avg = Math.floor(this.calculateExpectation(data) / 1024);
      // 한달 평균 총사용량
      const charge_mean = Math.floor(this.calculateMean(data) / 1024);
      // 최대 사용량
      const charge_max = Math.floor(this.calculateMax(data) / 1024);
      // 고객 업종
      const charge_cat = this.$store.getters.getResultData[0].customer_category;
      // QoS 해당 업종
      const charge_qos_cat = ['kiosk','media','vending','manufacturing']
      if (data.length === 0) return {};
      // 요금제 산정식
      if (charge_avg < 8) {
        charge_name = 'IoT38';
        charge_fee = '3,850(원)';
        charge_data = '8 MB';
      } else if (charge_avg < 50) {
        charge_name = 'IoT55';
        charge_fee = '5,500(원)';
        charge_data = '50 MB';
      } else if (charge_avg < 140) {
        charge_name = 'IoT77';
        charge_fee = '7,700(원)';
        charge_data = '140 MB';
      } else if (charge_avg < (1 * 1024)) {
        charge_name = 'IoT110';
        charge_fee = '11,000(원)';
        charge_data = '1 GB';
      } else if (charge_avg < (2 * 1024)) {
        charge_name = 'IoT165';
        charge_fee = '16,500(원)';
        charge_data = '2 GB';
      // QoS 요금제 
      } else if ( (charge_avg < (3 * 1024)) && (charge_max > (2.5 * charge_mean)) && (charge_qos_cat.includes(charge_cat)) ) {
        charge_name = 'IoT QoS 260';
        charge_fee = '26,000(원)';
        charge_data = '3 GB';
      } else if (charge_avg < (3 * 1024)) {
        charge_name = 'IoT220';
        charge_fee = '22,000(원)';
        charge_data = '3 GB';
      } else if (charge_avg < (3.5 * 1024)) {
        charge_name = 'IoT247';
        charge_fee = '24,750(원)';
        charge_data = '3.5 GB';
      } else if (charge_avg < (4 * 1024)) {
        charge_name = 'IoT275';
        charge_fee = '27,500(원)';
        charge_data = '4 GB';
      } else if (charge_avg < (5 * 1024)) {
        charge_name = 'IoT302';
        charge_fee = '30,250(원)';
        charge_data = '5 GB';
      } else if (charge_avg < (6 * 1024)) {
        charge_name = 'IoT330';
        charge_fee = '33,000(원)';
        charge_data = '6 GB';
      } else if ((charge_avg < (10 * 1024)) && (charge_max > (2.5 * charge_mean)) && (charge_qos_cat.includes(charge_cat)) ) {
        charge_name = 'IoT QoS 605';
        charge_fee = '60,500(원)';
        charge_data = '10 GB';
      } else if (charge_avg < (10 * 1024)) {
        charge_name = 'IoT550';
        charge_fee = '55,000(원)';
        charge_data = '10 GB';
      } else if (charge_avg < (20 * 1024)) {
        charge_name = 'IoT880';
        charge_fee = '88,000(원)';
        charge_data = '20 GB';
      } else if ((charge_avg < (30 * 1024)) && (charge_max > (2.5 * charge_mean)) && (charge_qos_cat.includes(charge_cat)) ) {
        charge_name = 'IoT QoS 1100';
        charge_fee = '110,000(원)';
        charge_data = '30 GB';
      } else {
        charge_name = 'IoT1045';
        charge_fee = '104,500(원)';
        charge_data = '30 GB';
      }
      return {
        recommendName: charge_name,
        recommendFee: charge_fee,
        recommendData: charge_data
      };
    }
  },
  mounted() {
    try {
        setTimeout(async()=>{
          // 1. Vuex에서 데이터 가져오기
          const result = this.getResultData;
          this.chartData = {
                    labels: result.map(item => item.usage_date),
                    datasets: [
                        {
                            label:"일일 데이터 사용량",
                            backgroundColor:'#f83823',
                            data: result.map(item => item.usage_per_day)
                        }
                    ]
                }
          this.statistics = this.calculateStatistics();
          this.recommends = this.recommendCharge();
          // 2. Vuex에 추천 요금제 이력 저장 >> 조회이력 페이지용
          const customer_name = this.$store.getters.getResultData[0].customerId;
          const customer_category = this.$store.getters.getResultData[0].customer_category;
          const historyData = {
            plan: this.recommends,
            customer: { name: customer_name, category: customer_category }
          };
          this.saveHistoryData(historyData);
        }, 0);
    } catch(error) {
        console.log(error)
    }
  },
};
</script>

<template>
  <div class="page-wrapper">
    <main>
      <!-- 요금제 항목 -->
      <section class="recommend-section">
        <div class="recommend-card">
          <h2>추천 요금제</h2>
          <p>{{ recommends ? recommends.recommendName : '계산 중' }}</p>
        </div>
        <div class="recommend-card">
          <h2>기본 요금(월)</h2>
          <p>{{ recommends ? recommends.recommendFee : '계산 중' }}</p>
        </div>
        <div class="recommend-card">
          <h2>기본 데이터</h2>
          <p>{{ recommends ? recommends.recommendData : '계산 중' }}</p>
        </div>
      </section>
      <!-- 데이터 통계 항목 -->
      <section class="statistics-section">
        <div class="statistics-card">
          <div style="width: 100%; height: 250px;">
            <h2>데이터 사용량</h2>
            <Bar v-bind:data="chartData" :options="chartOptions"/>
          </div>
        </div>
        <div class="statistics-card">
          <h2>기초 통계량</h2>
          <div>
            <p>예상 사용량: {{ statistics ? statistics.expectation : '계산 중' }} MB</p>
            <p>평균: {{ statistics ? statistics.mean : '계산 중' }} MB</p>
            <p>최대값: {{ statistics ? statistics.max : '계산 중' }} MB</p>
            <p>최소값: {{ statistics ? statistics.min : '계산 중' }} MB</p>
            <p>표본 분산: {{ statistics ? statistics.sampleVariance : '계산 중' }} MB</p>
            <p>표준 편차: {{ statistics ? statistics.standardDeviation : '계산 중' }} MB</p>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
.page-wrapper {
  padding: 20px;
  background-color: #f7f7f7;
}

main {
  padding-top: 30px;
}

.recommend-section {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 30px;
  margin-top: 50px;
}

.recommend-card {
  background-color: #ffffff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  padding: 20px;
  width: 30%;
  text-align: center;
  margin-bottom: 20px;
}

.recommend-card h2 {
  font-size: 18px;
  margin-bottom: 10px;
}

.recommend-card p {
  font-size: 16px;
  color: #555;
}

.statistics-section {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.statistics-card {
  background-color: #ffffff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  padding: 20px;
  width: 45%;
  margin-bottom: 20px;
}

.statistics-card h2 {
  font-size: 18px;
  margin-bottom: 15px;
}

.statistics-card p {
  font-size: 14px;
  color: #333;
}

@media (max-width: 768px) {
  .recommend-card, .statistics-card {
    width: 100%;
  }
}
</style>
