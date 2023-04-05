<template>
  <div class="musicPlayer">
    <div class="musicPlayerLayout">
      <audio ref="audio"
             :src="`https://music.163.com/song/media/outer/url?id=${store.state.musicModule.musicList[store.state.musicModule.musicIndex].id}.mp3`"
             id="mp"
             autoplay
             @timeupdate="updateTime"
             @ended="ended"
             class="mp"></audio>
      <div @click="openMusicDrawer" :class="{musicImg_active:store.state.musicModule.isMusicPlaying,
                    musicImg:!store.state.musicModule.isMusicPlaying}">
        <img :src="store.state.musicModule.musicList[store.state.musicModule.musicIndex].al.picUrl" alt="">
      </div>
      <div class="musicName">{{store.state.musicModule.musicList[store.state.musicModule.musicIndex].name}}</div>
      <div class="playOrPause">
        <el-icon @click="goPlay(-1)"><CaretLeft/></el-icon>
        <el-icon v-show="store.state.musicModule.isMusicPlaying===false"  @click="musicOn_Off"><VideoPlay /></el-icon>
        <el-icon v-show="store.state.musicModule.isMusicPlaying===true"   @click="musicOn_Off"><VideoPause /></el-icon>
        <el-icon @click="goPlay(1)"><CaretRight/></el-icon>
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
import {VideoPlay,VideoPause,Expand,CaretLeft,CaretRight} from '@element-plus/icons-vue'
import {nextTick, onMounted, onUpdated, ref} from "vue";

let audio = ref()
let isShowMusicList = ref(false)
let direction = ref('btt')

const openMusicDrawer = () => {
  store.commit('musicModule/updateIsMusicDrawerShow',true)
  store.commit('musicModule/updateIsMusicPlayerShow',false)
  store.commit('musicModule/updateDuration',audio.value.duration)
}
const musicOn_Off = () => {
  let myAudio = document.getElementById('mp')
  if(myAudio.paused){
    myAudio.play()
    store.commit("musicModule/updateIsMusicPlaying",true)
  }else {
    myAudio.pause()
    store.commit("musicModule/updateIsMusicPlaying",false)
  }
}
//音乐播放结束之后的处理事件
const ended = () => {
  //当前歌曲播放完毕后
  switch (store.state.musicModule.playMode) {
    case 0:{//顺序播放
      goPlay(1)
    }break;
    case 1:{//随机播放
      let randomNum = Math.floor(Math.random() *(store.state.musicModule.musicList.length))
      if(randomNum === store.state.musicModule.musicIndex){
        while (randomNum === store.state.musicModule.musicIndex){
          randomNum = Math.floor(Math.random() *(store.state.musicModule.musicList.length))
        }
        goPlay(randomNum)
      }else {
        goPlay(randomNum)
      }
    }break;
    case 2:{//单曲循环
      goPlay(-1)
      nextTick(()=>{
        goPlay(1)
      })
    }break;
  }
}
const goPlay = (e) => {
  let index = store.state.musicModule.musicIndex
  let length = store.state.musicModule.musicList.length
  store.commit('musicModule/updateMusicIndex',(index + e + length)%length)
  store.commit('musicModule/updateIsMusicPlaying',true)
}
//更新音乐当前时间戳和总时间
const updateTime = (e) => {
  store.commit('musicModule/updateCurrentTime',e.target.currentTime)
  store.commit('musicModule/updateDuration',audio.value.duration)
}
//切换歌曲更新歌词,本vue中的组件发生处于更新阶段时执行
onUpdated(()=>{
  store.dispatch('musicModule/getLyric',store.state.musicModule.musicList[store.state.musicModule.musicIndex].id)
})
onMounted(()=>{
  let myAudio = document.getElementById('mp')
  myAudio.pause()
  store.commit('musicModule/updateIsMusicPlaying',false)
  store.dispatch('musicModule/getLyric',store.state.musicModule.musicList[store.state.musicModule.musicIndex].id)
})
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