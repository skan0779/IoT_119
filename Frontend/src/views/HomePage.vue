<script setup>
</script>

<template>
  <div class="index-page">
    <main class="main">
      <!-- 홈페이지 항목 -->
      <section class="content">
        <div class="main-content">
          <div class="main_text">
            <h2>
              KT <br/>
              IoT 119 <br/>
              Service
            </h2>
            <p>
              "다양한 유형의 고객에게 운영 ▪ 제공 경험을 기반한 <br/>
              맞춤형 요금제를 제공해 드립니다"
            </p>
            <button @click="$router.push('/customer')" class="btn-submit">
              요금제 분석하기 <br/>
              (바로가기)
            </button>
          </div>
          <div class="main-img">
            <img src="../assets/homepage_iot.png" alt="iot_icon" width="300" height="200"/>
          </div>
        </div>
      </section>

    </main>
  </div>
</template>

<style scoped>

main {
  padding-top: 45px;
}
.banner {
  background-image: url('../assets/homepage_background.webp');
  background-size: cover;
  background-position: center;
  height: 160px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
  color: rgb(92, 89, 89);
  text-align: left;
}
.banner .banner_content {
  width: 250px;
  margin-left: 180px;
}
.banner h2 {
  font-size: 25px;
  font-weight: 500;
  margin-bottom: 0;
}
.banner p {
  font-size: 10px;
  font-weight: 600;
  margin-bottom: 0;
}
.content {
  padding: 40px 20px;
}
.main-content {
  display: flex;
  max-width: 800px;
  margin: 0 auto;
  background-color: rgba(220, 220, 220, 0.429);
  padding: 40px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 5%;
}
.main-content .main_text {
  margin-right: 110px;
}
.main-content h2 {
  font-size: 29px;
  font-weight: bold;
  margin-bottom: 10px;
}
.main-content p {
  font-size: 13px;
  font-weight: bold;
  line-height: 1.5;
  margin-bottom: 13px;
}
.main-content button {
  font-size: 8px;
  font-weight: 600;
  line-height: 1.5;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
.btn-submit {
  background-color: #007bff;
  color: white;
  padding: 5px 20px;
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
</style>