<template>
  <headerNav></headerNav>
  <div style="padding-top: 4rem">
    <el-button @click="qrLogin">获取二维码</el-button>
  </div>
  <img id="qrimg">
  <div id="qrStatus">12</div>
</template>


<script setup>
import headerNav from "@/components/headerNav";
import {checkQr, getQrKey, getQr, getLoginStatus} from "@/request/api/music";
import {useRouter} from "vue-router/dist/vue-router";
let qrkey
let timer
const router = useRouter()
const qrLogin = async () => {
  let res = await getQrKey()
  qrkey = res.data.data.unikey
  let res2 = await getQr(qrkey)
  document.querySelector('#qrimg').src = res2.data.data.qrimg
  timer = setInterval(async () => {
    let qrStatus = await checkQr(qrkey)
    if (qrStatus.data.code === 800){
      // message:"二维码不存在或已过期"
      clearInterval(timer)
    }
    else if (qrStatus.data.code === 801){
      document.querySelector('#qrStatus').innerText = '等待扫码'
    }
    else if (qrStatus.data.code === 802){
      // message:"授权中"
      // nickname:"XXX"
      document.querySelector('#qrStatus').innerText = '等待确定'
    }
    else if (qrStatus.data.code === 803){
      // message :"授权登陆成功"
      clearInterval(timer)
      localStorage.setItem('cookie', qrStatus.data.cookie)
      let info = await getLoginStatus(qrStatus.data.cookie)
      console.log(info)
      // await router.push('home')
    }
  }, 3000)
}
</script>
<style scoped>

</style>