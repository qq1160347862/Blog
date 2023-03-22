<template>
<!--  <div class="test" @click="clearLocal">管理员页面</div>-->
  <div class="adminContainer">
    <adminTopNav></adminTopNav>
    <div class="adminContainerBottom">
      <adminSideNav class="adminSideNav"></adminSideNav>
      <router-view></router-view>
    </div>

  </div>
</template>

<script>
export default {
  name: "admin"
}
</script>
<script setup>
import adminSideNav from "@/components/admin/adminSideNav";
import adminTopNav from '@/components/admin/adminTopNav'
const clearLocal = () => {
  localStorage.removeItem("token")
}
</script>

<style scoped>
.test {
  height: 50rem;
  width: 50rem;
  background-color: #3c99f6;
}
.adminContainer {
  width: 100vw;
  height: 100vh;

  display: flex;
  flex-direction: column;
}
.adminSideNav {
}
.adminContainerBottom {
  display: flex;
  flex-direction: row;
}
</style>