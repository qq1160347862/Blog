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
                      @focus="openMusicSearchBar"
                      ref="musicSearchInput"
                      @keyup.enter="musicSearch"
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
            <el-scrollbar>
              <div class="exitResult">
                <div @click="closeMusicSearchBar">
                  <el-icon><Close /></el-icon>
                </div>
              </div>
            </el-scrollbar>
          </div>
          <div class="msd_suggest"
               v-show="index_to_suggest === true
               && result === false" >
            <el-scrollbar>
              <div class="exitResult">
                <div @click="closeMusicSearchBar">
                  <el-icon><Close /></el-icon>
                </div>
              </div>
              <div class="suggestionItem"
                   @click="musicSearch"
                   v-for="item in suggestionList">
                <el-icon><Search /></el-icon>
                <span>{{item.keyword}}</span>
              </div>
            </el-scrollbar>
          </div>
          <div class="msd_result" v-show="result === true">
            <el-scrollbar @scroll="loadingSongsData" view-class="resultScroll">
              <div class="exitResult">
                <div @click="closeMusicSearchBar">
                  <el-icon><Close /></el-icon>
                </div>
                <div @click="exitResult">
                  <el-icon><Back /></el-icon>
                </div>
              </div>
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
import {nextTick, onMounted, reactive, ref, watch} from 'vue'
import store from "@/store";
import {ArrowDownBold, Search, Back, Close} from "@element-plus/icons-vue";
import {getMusicSearched, getSearchWordDefault, getSuggestWord} from "@/request/api/music";
import {debounce} from 'lodash'
import $ from 'jquery'
import {ElLoading} from "element-plus";

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
const musicSearch = async () => {
  result.value = true
  let keywords = ''
  if (musicSearchWords.value === '' || musicSearchWords.value === undefined){
    keywords = musicSearchWords_rec.value
    musicSearchWords.value = keywords
    requestData.keywords = keywords
  }else {
    keywords = musicSearchWords.value
    requestData.keywords = keywords
  }
  let res = await getMusicSearched(keywords,requestData.limit,requestData.offset)
  musicList.value = res.data.result.songs
}
const exitResult = () => {
  result.value = false
}
//选择音乐并播放
const selectMusic = (e) => {
  store.commit('musicModule/addMusicToMusicListHead',e)
  store.commit('musicModule/updateIsMusicPlaying',true)
  console.log(store.state.musicModule.musicList)
  console.log(store.state.musicModule.musicIndex)
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

//获取默认搜索推荐词
onMounted(async () => {
  let res = await getSearchWordDefault()
  if (res.data.code === 200){
    musicSearchWords_rec.value = res.data.data.realkeyword
  }else {
    console.log("关键词获取失败")
  }
})
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
  border-radius: .8rem;
  position: absolute;
  top: 42%;
  left: 50%;
  transform: translate(-50%,-50%);
  opacity: 1;

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
</style>