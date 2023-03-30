<template>
  <div>
    <el-drawer  v-model="store.state.musicModule.isMusicDrawerShow"
                :with-header="false"
                size="100%"
                :destroy-on-close="true"
                :close-on-click-modal="false"
                :close-on-press-escape="false"
                :direction="direction">
      <div @click="closeMusicDrawer">关闭抽屉</div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "musicDrawer"
}
</script>
<script setup>
import {ref} from 'vue'
import store from "@/store";
let direction = ref('btt')
const closeMusicDrawer = () => {
  store.commit('musicModule/updateIsMusicDrawerShow',false)
  store.commit('musicModule/updateIsMusicPlayerShow',true)
}
</script>

<style scoped>

</style>