<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div>
    <header>
      <!-- 홈 아이콘 항목 -->
      <RouterLink to="/">
        <div class="header_logo">
          <img alt="kt enterprise logo" class="logo" src="@/assets/kt_enterprise_logo.png" width="102" height="20" />
        </div>
      </RouterLink>
      <!-- 네비게이션 항목 -->
      <div class="header_gnb">
        <nav>
          <ul>
            <li>
              <RouterLink to="/">
                <div class="icon">
                  <img src="@/assets/homepage_home_icon.png" alt="home_icon" />
                </div>
                홈페이지
              </RouterLink>
            </li>
            <li>
              <RouterLink to="/customer">
                <div class="icon">
                  <img src="@/assets/homepage_customer_icon.png" alt="customer_icon" />
                </div>
                고객정보
              </RouterLink>
            </li>
            <li>
              <RouterLink to="/charge">
                <div class="icon">
                  <img src="@/assets/homepage_charge_icon.png" alt="charge_icon" />
                </div>
                요금제추천
              </RouterLink>
            </li>
            <li>
              <RouterLink to="/history">
                <div class="icon">
                  <img src="@/assets/homepage_history_icon.png" alt="history_icon" />
                </div>
                조회이력
              </RouterLink>
            </li>
          </ul>
        </nav>
      </div>
    </header>
    <RouterView />  
  </div>
</template>

<style scoped>

header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 80px;
  background-color: #ffffff;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 15%;
  z-index: 1000;
}

header .header_logo {
  margin-left: 100;
  margin-top: 15px;
}

nav ul {
  display: flex;
  list-style: none;
  gap: 30px;
}

nav ul li {
  margin: 0;
  margin-top: 8%;
}

nav ul li a {
  text-decoration: none;
  color: #484848;
  font-weight: 600;
  font-size: 10px;
  padding: 10px 0px;
  transition: color 0.3s ease;
}

nav ul li a:hover {
  color: #5c859f; 
}

.icon {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 2px;
}

.icon img {
  width: 22px;
  height: 22px;
  transition: transform 0.3s ease;
}

nav ul li a:hover .icon img {
  transform: scale(1.1);
}

</style>
