<template>
  <div class="musicPlayer">
    <div class="musicPlayerLayout">
      <audio ref="audio" id="mp" class="mp"
             loop></audio>
<!--             @timeupdate="updateTime"-->
<!--             @ended="ended"-->
      <div :class="{musicImg_active:store.state.musicModule.isMusicPlaying,musicImg:!store.state.musicModule.isMusicPlaying}">
        <img src="../assets/headimg.jpg" alt="">
      </div>
      <div class="musicName">音乐名称</div>
      <div class="playOrPause">
        <el-icon v-show="store.state.musicModule.isMusicPlaying===false"  @click="musicOn_Off"><VideoPlay /></el-icon>
        <el-icon v-show="store.state.musicModule.isMusicPlaying===true"   @click="musicOn_Off"><VideoPause /></el-icon>
        <el-icon><Expand /></el-icon>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "musicPlayer",
  // methods:{
  //   musicOn_Off(){
  //     let myAudio = document.getElementById('mp')
  //     console.log("123")
  //     if(myAudio.paused){
  //       myAudio.play()
  //       store.commit("updateIsMusicPlaying",true)//更新store中的isPlaying状态
  //     }else {
  //       myAudio.pause()
  //       store.commit("updateIsMusicPlaying",false)
  //     }
  //   }
  // }
}
</script>
<script setup>
import store from '@/store'
import {VideoPlay,VideoPause,Expand} from '@element-plus/icons-vue'
import mp3 from '../assets/music.mp3'

const musicOn_Off = () => {
  let myAudio = document.getElementById('mp')
  myAudio.src = mp3
  if(!store.state.musicModule.isMusicPlaying){
    myAudio.play()
    store.commit("musicModule/updateIsMusicPlaying",true)
  }else {
    myAudio.pause()
    store.commit("musicModule/updateIsMusicPlaying",false)
  }
}

</script>
<style scoped>
.musicPlayer {
  width: 50vw;
  height: 10vh;
  background-color: #ffffff;
  border-radius: 1.6rem 1.6rem 0 0;
}
.musicPlayerLayout {
  display: flex;
  align-items: center;
  width: 100%;
  height: 100%;
  position: relative;
}
.musicPlayerLayout>.musicImg_active{
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: musicPicRotate 10s linear infinite normal;
}
.musicPlayerLayout>.musicImg {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;

  border-radius: 50%;
  animation: musicPicRotate 10s linear infinite normal;
  animation-play-state: paused !important;
}
.musicPlayerLayout>.musicImg img,
.musicPlayerLayout>.musicImg_active img{

  width: 8vh;
  height: 8vh;
  border-radius: 50%;

}
.musicPlayerLayout>.musicName {
  flex: 1;
  color: #2c2c2c;
  /*background-color: #2c2c2c;*/
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  font-weight: bold;
  font-size: 2vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.musicPlayerLayout>.playOrPause {
  flex: 1;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  /*background-color: blue;*/
  border-radius: 50%;
  font-size: 5vh;
  color: #2c2c2c;
  transition: .3s;
}



@keyframes musicPicRotate {
  from{
    transform: rotate(0deg);
    width: 4rem;
  }
  to {
    transform: rotate(360deg);
  }
}
</style>