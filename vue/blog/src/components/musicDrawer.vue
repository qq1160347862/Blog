<template>
  <div class="musicDrawer">
    <el-drawer  v-model="store.state.musicModule.isMusicDrawerShow"
                :with-header="false"
                size="100%"
                :destroy-on-close="true"
                :close-on-click-modal="false"
                :close-on-press-escape="false"
                :direction="direction">
      <div class="musicDrawerContainer">
        <div class="musicDrawerTop">
          <el-icon @click="closeMusicDrawer"><ArrowDownBold /></el-icon>
          <div class="musicSearchBar">
            <el-input v-model="musicSearchWords"
                      size="large"
                      clearable
                      @focus="openMusicSearchBar"
                      ref="musicSearchInput"
                      @keyup.enter="musicSearch(null)"
                      :prefix-icon="Search"
                      :placeholder="'大家都在搜 ' + musicSearchWords_rec" />
          </div>
        </div>
        <div :class="{musicSearchBarDrawer:isShowMusicSearchDrawer,
                musicSearchBarDrawer_close:!isShowMusicSearchDrawer}"
             @blur="closeMusicSearchBar">
          <div class="msd_index"
               v-show="index_to_suggest === false
               && result === false">
            <div class="exitResult">
              <div @click="closeMusicSearchBar">
                <el-icon><Close /></el-icon>
              </div>
            </div>
            <div style="height: 2rem;
                        font-weight: bold;
                        font-size: 1.2rem">热搜榜</div>
            <el-scrollbar>
              <div class="hotMusicList" v-if="hotMusicList.length > 0 || hotMusicList.value !== null">
                <div class="hotMusicListItem"
                     @click="musicSearch(item.searchWord)"
                     v-for="(item,index) in hotMusicList">
                  <div :class="{hotMusicListItem_index:(index + 1) > 3,
                                hotMusicListItem_index_red:(index + 1) <= 3}">
                    {{index + 1}}
                  </div>
                  <div class="hotMusicListItemInfos">
                    <span :class="{mn:(index + 1) > 3,mn_bold:(index + 1) <= 3}">{{item.searchWord}}</span>
                    <span class="ms">{{item.score}}</span>
                    <img v-if="item.iconUrl !== null" :src="item.iconUrl" alt="">
                    <div class="mDesc" v-if="item.content !== '' || item.content !== null">
                      <span>{{item.content}}</span>
                    </div>
                  </div>
                </div>
              </div>
            </el-scrollbar>
          </div>
          <div class="msd_suggest"
               v-show="index_to_suggest === true
               && result === false" >
            <div class="exitResult">
              <div @click="closeMusicSearchBar">
                <el-icon><Close /></el-icon>
              </div>
            </div>
            <el-scrollbar>
              <div class="suggestionItem"
                   @click="musicSearch(item.keyword)"
                   v-for="item in suggestionList">
                <el-icon><Search /></el-icon>
                <span>{{item.keyword}}</span>
              </div>
            </el-scrollbar>
          </div>
          <div class="msd_result" v-show="result === true">
            <div class="exitResult">
              <div @click="closeMusicSearchBar">
                <el-icon><Close /></el-icon>
              </div>
              <div @click="exitResult">
                <el-icon><Back /></el-icon>
              </div>
            </div>
            <el-scrollbar @scroll="loadingSongsData" view-class="resultScroll">
              <div class="resultItem"
                   v-if="musicList !== null || musicList.length > 0"
                   v-for="(item,index) in musicList"
                   :key="index"
                   @click="selectMusic(item)">
                <span>{{item.name}}</span>
              </div>
            </el-scrollbar>
          </div>
        </div>
        <div class="musicDrawerMiddle">
          <div class="musicInfos">
            <div class="musicName">
              {{store.state.musicModule.musicList[store.state.musicModule.musicIndex].name}}
            </div>
            <div class="singerName">
              <span v-for="item in store.state.musicModule.musicList[store.state.musicModule.musicIndex].ar">
              {{item.name}}
            </span>
            </div>
          </div>
          <div class="musicImgContainer">
            <img :class="{needle_ab_active:store.state.musicModule.isMusicPlaying,needle_ab:!store.state.musicModule.isMusicPlaying}"
                 src="../assets/needle-ab.png" alt="">
            <div class="whiteCircle"></div>
            <img class="blackCircle" src="../assets/blackCircle.png" alt="">
            <img :class="{musicPic_active:!store.state.musicModule.isMusicPlaying,musicPic:store.state.musicModule.isMusicPlaying}"
                 :src="store.state.musicModule.musicList[store.state.musicModule.musicIndex].al.picUrl"
                 alt="">
          </div>
          <div class="musicLyricContainer">
            <p v-for="item in lyric" :key="item"
               :class="{lyricActive:(store.state.musicModule.currentTime*1000)>=item.time && (store.state.musicModule.currentTime*1000)<=item.pre}">
              {{item.lrc}}
            </p>
          </div>
        </div>
        <div class="musicDrawerBottom">
          <div class="musicProgressBar">
            <el-slider v-model="myAudio.currentTime"
                       :min=0 :max="store.state.musicModule.duration" :step=0.5 />
          </div>
          <div class="musicOperate">
            <div class="lastPlay">
              <el-icon @click="goPlay(-1)"><CaretLeft/></el-icon>
            </div>
            <div class="isPlay">
              <el-icon v-show="store.state.musicModule.isMusicPlaying===false"  @click="musicOn_Off"><VideoPlay /></el-icon>
              <el-icon v-show="store.state.musicModule.isMusicPlaying===true"   @click="musicOn_Off"><VideoPause /></el-icon>
            </div>
            <div class="nextPlay">
              <el-icon @click="goPlay(1)"><CaretRight/></el-icon>
            </div>
          </div>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "musicDrawer",
}
</script>
<script setup>
import {computed, nextTick, onMounted, onUpdated, reactive, ref, watch} from 'vue'
import store from "@/store";
import {ArrowDownBold, Search, Back, Close, VideoPlay, VideoPause, CaretLeft, CaretRight} from "@element-plus/icons-vue";
import {getMusicSearched, getSearchHotDetail, getSearchWordDefault, getSuggestWord} from "@/request/api/music";
import {debounce} from 'lodash'
import $ from 'jquery'
import {ElLoading} from "element-plus";
//音乐播放器
const myAudio = document.getElementById('mp')
let result = ref(false)
let index_to_suggest = ref(false)
let suggestionList = ref([])  //reactive不能双向绑定
let musicSearchInput = ref()
let direction = ref('btt')
let musicSearchWords = ref('')
let musicSearchWords_rec = ref('')
let isShowMusicSearchDrawer = ref(false)
let requestData = reactive({
  keywords:'',
  limit:20,
  offset:1,
  page:0
})
let musicList = ref([])
let hotMusicList = ref([])
let isLoadingComments = ref(false)
let blockInfer = ref(false)
const closeMusicDrawer = () => {
  store.commit('musicModule/updateIsMusicDrawerShow',false)
  store.commit('musicModule/updateIsMusicPlayerShow',true)
  isShowMusicSearchDrawer.value = false
}
const openMusicSearchBar = () => {
  isShowMusicSearchDrawer.value = true
}
const closeMusicSearchBar = () => {
  isShowMusicSearchDrawer.value = false
  result.value = false
}
const musicSearch = async (e) => {
  result.value = true
  let keywords = ''

  if (e === null){//回车搜索
    if (musicSearchWords.value === '' || musicSearchWords.value === undefined){
      keywords = musicSearchWords_rec.value
      musicSearchWords.value = keywords
      requestData.keywords = keywords
    }else {
      keywords = musicSearchWords.value
      requestData.keywords = keywords
    }
  }else {//建议选项搜索
    keywords = e
    musicSearchWords.value = keywords
    requestData.keywords = keywords
  }

  let res = await getMusicSearched(keywords,requestData.limit,requestData.offset)
  musicList.value = res.data.result.songs
}
const musicOn_Off = () => {
  if(myAudio.paused){
    myAudio.play()
    store.commit("musicModule/updateIsMusicPlaying",true)
  }else {
    myAudio.pause()
    store.commit("musicModule/updateIsMusicPlaying",false)
  }
}
const goPlay = (e) => {
  let index = store.state.musicModule.musicIndex
  let length = store.state.musicModule.musicList.length
  store.commit('musicModule/updateMusicIndex',(index + e + length)%length)
  store.commit('musicModule/updateIsMusicPlaying',true)
}
const exitResult = () => {
  result.value = false
}
//选择音乐并播放
const selectMusic = (e) => {
  store.commit('musicModule/addMusicToMusicListHead',e)
  store.commit('musicModule/updateIsMusicPlaying',true)
}
//歌曲滑动加载
const loadingSongsData = async (scroll) => {
  let sightArea = $('.msd_result')
  let contentArea = $('.el-scrollbar__view.resultScroll')
  let index = (contentArea.length-1)
  let scrollHeight = contentArea[index].scrollHeight
  let infer = (scrollHeight-scroll.scrollTop)-sightArea[0].scrollHeight
  //异步先初始好值
  let init = function () {
    sightArea = $('.msd_result')
    contentArea = $('.el-scrollbar__view.resultScroll')
    index = (contentArea.length-1)
    scrollHeight = contentArea[index].scrollHeight
    infer = (scrollHeight-scroll.scrollTop)-sightArea[0].scrollHeight
  }
  await init()


  if (infer <= 0 && blockInfer.value===false){
    isLoadingComments.value = true
    blockInfer.value = true
  }
  if(infer <= 0 && isLoadingComments.value){
    isLoadingComments.value = false
    requestData.page = requestData.page + 1
    requestData.offset = requestData.page * requestData.limit
    let res = await getMusicSearched(requestData.keywords,requestData.limit,requestData.offset)
    // console.log(requestData.page,requestData.offset)
    if(res.data.result.songCount > 0){
      res.data.result.songs.forEach(item => {
        musicList.value.push(item)
      })

    }else {
      alert("没有歌曲了")
    }
    blockInfer.value = false
  }
}
//歌词
const lyric = computed(() => {
  let arr
  if (store.state.musicModule.lyricList.lyric){
    arr = store.state.musicModule.lyricList.lyric.split(/[(\r\n)\r\n]+/).map((item)=>{
      let minute = item.slice(1,3)
      let second = item.slice(4,6)
      let mill = item.slice(7,10)
      let lrc = item.slice(11,item.length)
      let time = parseInt(minute)*60*1000+parseInt(second)*1000+parseInt(mill)
      if(isNaN(Number(mill))){
        mill = item.slice(7,9)
        lrc = item.slice(10,item.length)
        time = parseInt(minute)*60*1000+parseInt(second)*1000+parseInt(mill)
      }
      return {minute,second,mill,lrc,time}
    })
  }
  // arr.forEach((item,i) => {
  //   if (i === arr.length-1 || isNaN(arr[i+1].time)){  //  当是最后一句歌词，下一个时间段置为0
  //     item.pre = 0
  //   }else {   //当不是最后一句歌词，下一个时间设置为下一句歌词的起始时间
  //     item.pre = arr[i+1].time
  //   }
  // })
  for (let i = 0; i < arr.length; i++) {
    if (i == arr.length-1 || isNaN(arr[i+1].time)){
      arr[i].pre = 0
    }else {
      arr[i].pre = arr[i+1].time
    }
  }
  // console.log(arr)
  return arr
})
//监听歌词，实现滚动
watch(()=>store.state.musicModule.currentTime,(newValue,oldValue)=>{
  let p = document.querySelector("p.lyricActive")
  if (p!=null ){
    // setTimeout(()=>{
    //
    // },500)
    if ($('.musicLyricContainer')[0] != null){
      $('.musicLyricContainer')[0].scrollTo({
        top: p.offsetTop-350,
        left:0,
        behavior:'smooth'
      })
    }
  }
},{})
//动态监听musicSearchWords
watch(musicSearchWords,debounce(async (newValue,oldvalue)=>{
  let res = await getSuggestWord(musicSearchWords.value)
  if(res.data.result!==null && res.data.result !==undefined){
    if (res.data.result.allMatch !== undefined){
      suggestionList.value = res.data.result.allMatch
      index_to_suggest.value = true
    }
  }else {
    console.log("搜索建议数据为空，不更新")
  }
  if (musicSearchWords.value === ''){
    index_to_suggest.value = false
  }
},200),{})
//获取默认搜索推荐词
onMounted(async () => {
  let res = await getSearchWordDefault()
  if (res.data.code === 200){
    musicSearchWords_rec.value = res.data.data.showKeyword
  }else {
    console.log("关键词获取失败")
  }
})
//获取热搜榜
onMounted(async () => {
  let res = await getSearchHotDetail()
  if (res.data.code === 200){
    hotMusicList.value = res.data.data
  }else {
    console.log("热搜榜获取失败")
  }
})
</script>

<style scoped>
.musicDrawer>>>.el-overlay,
.musicDrawer>>>.el-drawer {
  background-color: transparent;
}
.musicDrawer>>>.el-drawer__body {
  padding: 0;
}
.musicDrawerContainer{
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(10px);
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.musicDrawerTop {
  width: 100%;
  height: 4rem;
  /*background-color: rgba(255, 255, 255, 0.5);*/
  display: flex;
  align-items: center;
  position: relative;
}
.musicDrawerTop>.el-icon{
  font-size: 1.6rem;
  margin-left: 1rem;
  color: #6e6c6c;
}
.musicDrawerTop>.musicSearchBar{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-50%);
}
.musicDrawerTop>.musicSearchBar>>>.el-input{
  --el-input-focus-border-color: rgba(0, 0, 0, 0.7);
  --el-input-hover-border-color: rgba(0, 0, 0, 0.7);
  --el-input-bg-color: rgba(0,0,0,0);
  --el-input-text-color: #000000;
  --el-input-placeholder-color: rgba(0, 0, 0, 0.5);
  --el-input-border-color: rgba(0, 0, 0, 0.4);
}
.musicDrawerTop>.musicSearchBar>>>.el-input__wrapper {
  border-radius: 32px;
}
.musicDrawerTop>.musicSearchBar>>>.el-input__prefix{
  color: black;
}
.musicDrawerTop>.musicSearchBar>>>.el-input__inner{
  color: black;
}
.musicSearchBarDrawer{
  width: 30%;
  height: 70%;
  background-color: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(10px);
  border-radius: .8rem;
  position: absolute;
  top: 42%;
  left: 50%;
  transform: translate(-50%,-50%);
  opacity: 1;
  z-index: 2050;

  transition: top 0.2s,opacity 0.2s 0.2s;
}
.musicSearchBarDrawer_close {
  width: 30%;
  height: 70%;
  background-color: rgba(255, 255, 255, 0.7);
  border-radius: .8rem;
  position: absolute;
  top: -142%;
  left: 50%;
  transform: translate(-50%,-50%);
  opacity: 0;
  transition: top 0.2s 0.2s,opacity 0.2s;
}
.msd_index{
  border-radius: .8rem;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.msd_index>>>.el-scrollbar{
  width: 100%;
}
.hotMusicList {
  width: 100%;
}
.hotMusicListItem {
  width: 100%;
  height: 5rem;
  display: flex;
  align-items: center;
  flex-direction: row;
}
.hotMusicListItem:hover{
  background-color: rgba(245, 108, 108, 0.1);
}
.hotMusicListItem_index {
  height: 100%;
  width: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;

  font-size: 1.2rem;
  color: rgba(0, 0, 0, 0.3);
  font-weight: bold;
}
.hotMusicListItem_index_red {
  height: 100%;
  width: 5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.2rem;
  color: rgb(253, 125, 125);
  font-weight: bold;
}
.hotMusicListItemInfos .mn{
  font-size: 1rem;
}
.hotMusicListItemInfos .mn_bold {
  font-weight: bold;
  font-size: 1rem;
}
.hotMusicListItemInfos .ms{
  margin-left: .5rem;
  font-size: 1rem;
  color: rgba(44, 44, 44, 0.6);
}
.hotMusicListItemInfos .mDesc{
  font-size: .8rem;
  color: rgba(44, 44, 44, 0.6);
}
.hotMusicListItemInfos img{
  height: 1rem;
}
.msd_suggest{
  border-radius: .8rem;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.msd_suggest>>>.el-scrollbar{
  width: 100%;
}
.msd_suggest .suggestionItem{
  width: 100%;
  height: 3rem;
  display: flex;
  align-items: center;
}
.msd_suggest .suggestionItem:hover span{
  /*background-color: rgba(231, 227, 227, 0.7);*/
  color: #3c99f6;
}
.msd_suggest .suggestionItem>>>.el-icon{
  margin-left: 1rem;
  font-size: 1.2rem;
}
.msd_suggest .suggestionItem span{
  padding-left: 1rem;
}
.exitResult {
  width: 100%;
  height: 3rem;
  display: flex;
  align-items: center;
  flex-direction: row-reverse;
}
.exitResult>div{
  display: flex;
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background-color: #FFFFFF;
  justify-content: center;
  align-items: center;
  margin-right: 0.8rem;
}
.exitResult>div:hover{
  background-color: #2c2c2c;
  color: #FFFFFF;
}
.exitResult>>>.el-icon {
  font-size: 1.2rem;
}
.msd_result{
  border-radius: .8rem;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.msd_result>>>.el-scrollbar {
  width: 100%;
}
.msd_result .resultItem {
  width: 100%;
  height: 3rem;
  display: flex;
  align-items: center;
}
.msd_result .resultItem:hover span {
  color: #3c99f6;
}
.msd_result .resultItem span{
  margin-left: 1rem;
}


.musicDrawerMiddle {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: calc(100% - 12rem);
  position: relative;
}
.musicDrawerMiddle .musicInfos {
  position: absolute;
  width: 12rem;
  top: 3rem;
  left: 34rem;
  transform: translateX(-50%);
  text-align: center;
}
.musicDrawerMiddle .musicInfos .musicName{
  font-size: 1.6rem;
  font-weight: bold;
}
.musicDrawerMiddle .musicInfos .singerName{
  margin-top: .5rem;
  font-size: .8rem;
}
.musicDrawerMiddle .musicInfos .singerName span{
  margin-right: .2rem;
}
.musicDrawerMiddle .musicImgContainer{
  height: 100%;
  width: 32rem;
  /*background-color: #f56c6c;*/

  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}
.needle_ab{
  width: 8rem;
  height: 12rem;
  position: absolute;
  left: 55%;
  top: 20%;
  transform: translate(-50%,-50%) rotate(-16deg);
  transform-origin: 0 0;
  transition: all .5s;
  z-index: -1;
  /*animation: needle-ab 1s ease-in-out normal forwards;*/
}
.needle_ab_active{
  width: 8rem;
  height: 12rem;
  position: absolute;
  left: 55%;
  top: 20%;
  transform: translate(-50%,-50%) rotate(6deg);
  transform-origin: 0 0;
  transition: all .5s;
  z-index: -1;
}
.whiteCircle {
  width: 25rem;
  height: 25rem;
  border-radius: 50%;
  background-color: #dbdcdb;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  z-index: -2;
}
.blackCircle{
  width: 24rem;
  height: 24rem;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  z-index: -2;
}
.musicPic {
  z-index: 0;
  position: absolute;
  width: 15rem;
  height: 15rem;
  border-radius: 50%;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  animation: musicPicRotate 10s linear infinite normal;
}
.musicPic_active{
  z-index: 0;
  position: absolute;
  width: 15rem;
  height: 15rem;
  border-radius: 50%;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
  animation: musicPicRotate 10s linear infinite normal;
  animation-play-state: paused !important;
}
.musicDrawerMiddle .musicLyricContainer {
  height: 60%;
  width: 32rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 8rem ;
  overflow: hidden;
}
.musicDrawerMiddle .musicLyricContainer p{
  color: rgba(0, 0, 0, 0.3);
  margin-bottom: 1.5rem;
  transition: all 1s;
  text-align: center;
  font-size: 1.2rem;
}
.lyricActive{
  color: rgba(0, 0, 0, 0.8) !important;
  font-size: 1.6rem !important;
  font-weight: bold;
  text-align: center;
}


.musicDrawerBottom {
  width: 100%;
  height: 8rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.musicDrawerBottom .musicProgressBar{
  width: 96%;
}
.musicDrawerBottom .musicProgressBar>>>.el-slider__runway{
  background-color: rgba(0, 0, 0, 0.8);
  height: 1px;
}
/*进度条颜色*/
.musicDrawerBottom .musicProgressBar>>>.el-slider__bar{
  /*background: #ffffff linear-gradient(rgba(0,0,0,1),rgba(0,0,0,1));*/
  /*animation: bar-anim 2s linear infinite;*/
  background-color: #3c99f6;
  height: 1px;
}
.musicDrawerBottom .musicProgressBar>>>.el-slider__button-wrapper{
  top: -17.5px;
}
.musicDrawerBottom .musicProgressBar>>>.el-slider__button{
  width: 10px;
  height: 10px;
  border: #ffffff solid 1px;
  box-shadow: rgba(255, 255, 255, 0.8) 0 0 1rem;
}
.musicDrawerBottom .musicOperate{
  width: 30%;
  height: 6rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
.musicDrawerBottom .musicOperate>>>.el-icon {
  font-size: 1.6rem;
}
.lastPlay,
.nextPlay {
  width: 2rem;
  height: 2rem;
  background-color: rgba(253, 251, 251, 0.8);
  margin: 0 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
}
.isPlay {
  width: 3rem;
  height: 3rem;
  background-color: rgba(244, 244, 244, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
}
.isPlay>>>.el-icon {
  font-size: 2.4rem !important;
}

.lastPlay:hover,
.isPlay:hover,
.nextPlay:hover{
  background-color: #2c2c2c;
  color: #f9f9f9;
}

@keyframes musicPicRotate {
  from{
    transform: translate(-50%,-50%) rotate(0deg);
  }
  to {
    transform: translate(-50%,-50%) rotate(360deg);
  }
}
</style>