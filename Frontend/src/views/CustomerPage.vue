<script>
import axios from 'axios';
import { mapActions } from 'vuex';

export default {
    data() {
      return {
        formData: {
          customer_name: "",
          customer_category: "",
          customer_data: null
        },
      };
  },
  methods: {
    // 1. Vuex 연결
    ...mapActions(['saveResultData']),
    // 2. 엑셀 업로드
    handleFileUpload(event) {
      this.formData.customer_data = event.target.files[0];
    },
    // 3. 폼 전송 >> Spring Boot로 전송
    async submitForm() {
      try {
        // 1. 데이터 생성
        const formData = new FormData();
        formData.append("customer_name", this.formData.customer_name);
        formData.append("customer_category", this.formData.customer_category);
        formData.append("customer_data", this.formData.customer_data);
        // 2. 서버로 request POST >> DB 저장
        const response = await axios.post("http://localhost:8080/api/upload", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        // 3. 서버에서 response를 Vuex에 저장 >> ChargePage로 전달
        this.saveResultData(response.data);
        // 4. ChargePage로 이동
        this.$router.push({name: 'charge'});
      } catch (error) {
        // 5. 오류시 알람
        console.error(error);
        alert("Submit Failed: Server Error");
      }
    },
  },
}
</script>

<template>
  <div>
    <main>
      <!-- 정보입력 항목 -->
      <section class="content">
        <div class="main-content">
          <div class="main_text">
            <h2>고객 정보 입력</h2>
            <p>"고객의 정보를 입력하고 최적의 요금제를 분석해 보세요"</p>
            <form v-on:submit.prevent="submitForm">
              <!-- 고객명 -->
              <div class="form-group">
                <label for="customer_name">고객명</label>
                <input id="customer_name" type="text" v-model="formData.customer_name" required class="form-control" />
              </div>
              <!-- 고객업종 -->
              <div class="form-group">
                <label for="customer_category">고객 업종</label>
                <select id="customer_category" v-model="formData.customer_category" required class="form-control">
                  <option value="" disabled>업종을 선택하세요</option>
                  <option value="charging">전기차충전관제</option>
                  <option value="metering">원격검침(가스/수도/전력)</option>
                  <option value="wifi">공공WiFi</option>
                  <option value="traffic">교통신호기관제</option>
                  <option value="vehicle">소형이동체관제</option>
                  <option value="kiosk">키오스크</option>
                  <option value="bis">BIS/BMS관제</option>
                  <option value="marine">해양IoT</option>
                  <option value="retail">물류▪유통</option>
                  <option value="media">미디어▪광고</option>
                  <option value="manufacturing">건설업</option>
                  <option value="restaurant">요식업</option>
                  <option value="cafe">카페</option>
                  <option value="vending">벤딩머신</option>
                </select>
              </div>
              <!-- 엑셀파일 -->
              <div class="form-group">
                <label for="customer_data">PoC 데이터 업로드 (엑셀파일)</label>
                <input type="file" id="customer_data" @change="handleFileUpload" accept=".xlsx, .xls" required class="form-control" />
              </div>
              <!-- 버튼 종합 -->
              <div class="button-group">
                <!-- Form 전송 -->
                <div class="form-group">
                  <button type="submit" class="btn-submit">결과보기</button>
                </div>
                <!-- 양식 다운로드 -->
                <div class="form-group">
                  <a href="../../public/PoC데이터양식_제조사명_고객명.xlsx" download="PoC데이터양식_제조사명_고객명.xlsx" class="btn-submit">
                    엑셀 양식 다운로드
                  </a>
                </div>
                
              </div>
            </form>
          </div>

          <div class="main-img">
            <img src="../assets/customerpage_charge.png" alt="charge_icon" width="250" height="170" />
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
/* 기본적인 페이지 레이아웃 스타일 */
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

.form-group {
  margin-bottom: 20px;
}

.form-control {
  width: 100%;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.form-control:focus {
  border-color: #007bff;
  outline: none;
}

.button-group {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 35px;
}

.btn-submit {
  background-color: #007bff;
  color: white;
  padding: 8px 20px;
  border: none;
  border-radius: 5px;
  font-size: 13px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-right: 15px;
}

.btn-submit:hover {
  background-color: #0056b3;
}

/* 엑셀 다운로드 버튼 */
.download-section {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
}

.btn-download {
  background-color: #007bff;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  text-decoration: none;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.btn-download:hover {
  background-color: #0056b3;
}

/* 이미지 섹션 */
.main-img {
  display: flex;
  align-items: center;
  justify-content: center;
}

.main-img img {
  max-width: 100%;
  height: auto;
}
</style>