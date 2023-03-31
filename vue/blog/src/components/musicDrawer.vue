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
            没有词语
          </div>
          <div class="msd_suggest"
               v-show="index_to_suggest === true
               && result === false" >
            <div class="suggestionItem"
                 @click="musicSearch"
                 v-for="item in suggestionList">
              <el-icon><Search /></el-icon>
              {{item.keyword}}
            </div>
          </div>
          <div class="msd_result" v-show="result === true">
            <div @click="exitResult">
              结果
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
import {onMounted, reactive, ref, watch} from 'vue'
import store from "@/store";
import {ArrowDownBold, Search} from "@element-plus/icons-vue";
import {getSearchWordDefault, getSuggestWord} from "@/request/api/music";
import {debounce} from 'lodash'

let result = ref(false)
let index_to_suggest = ref(false)
let suggestionList = ref([])  //reactive不能双向绑定
let musicSearchInput = ref()
let direction = ref('btt')
let musicSearchWords = ref('')
let musicSearchWords_rec = ref('')
let isShowMusicSearchDrawer = ref(false)
const closeMusicDrawer = () => {
  store.commit('musicModule/updateIsMusicDrawerShow',false)
  store.commit('musicModule/updateIsMusicPlayerShow',true)
}
const openMusicSearchBar = () => {
  isShowMusicSearchDrawer.value = true
}
const closeMusicSearchBar = () => {
  isShowMusicSearchDrawer.value = false
}
const musicSearch = () => {
  result.value = true
}
const exitResult = () => {
  result.value = false
}

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
  console.log(res.data.result)
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
  background-color: #f56c6c;
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
  background-color: #f56c6c;
  border-radius: .8rem;
  position: absolute;
  top: -142%;
  left: 50%;
  transform: translate(-50%,-50%);
  opacity: 0;
  transition: top 0.2s 0.2s,opacity 0.2s;
}
</style>