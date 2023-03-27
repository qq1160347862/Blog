<template>
  <div class="homeContainer">
    <headerNav></headerNav>
    <div class="homeBody">
      <el-scrollbar @scroll="scroll" ref="elScroll">
        <div class="homeBg">
          <div class="BgContentArea">
            <div class="bgLeft">
              <div class="headImgArea">
                <div class="userDesc">
                  “周围很嘈杂，听不清是风是寒流，还是她在吵架”
                </div>
              </div>
            </div>
            <div class="bgRight">

            </div>
          </div>
        </div>
        <div class="homeContent">
          <div class="homeContentLayout">
            <div class="hcLeft">
              <div class="sort">
                <ul>
                  <li @click="switchToRecommend" class="switchToRecommend">博主推荐</li>
                  <li @click="switchToHistory" class="switchToHistory">浏览最多</li>
                  <li @click="switchToRecent" class="switchToRecent">近期发布</li>
                </ul>
              </div>
              <div class="articleArea">
                <div class="articleCard" v-for="item in store.state.articleModule.articleList"
                    >
<!--                  {{item.articleId}}-->
<!--                  {{item.title}}-->
                  <div class="articleInfo">
                    <div class="articleTitle" @click="redirectArticleIndex(item.articleId)">
                      <span>{{item.title}}</span>
                    </div>
                    <div class="articleOther">
                      <span>
                        <el-icon><UserFilled/></el-icon>
                        {{item.userName}}
                      </span>
                      <span>
                        <el-icon><Calendar/></el-icon>
                        {{item.pubDate.substring(0,10)}}
                      </span>
                      <span>
                        <el-icon><FolderOpened/></el-icon>
                        {{item.sortName}}
                      </span>
                      <span>
                        <el-icon><Notebook/></el-icon>
                        {{item.labelName}}
                      </span>
                    </div>
                  </div>
                  <div class="articleTxT">
                    <div>{{item.content}}</div>
                  </div>
                </div>
                <div class="articlePager">
                  <el-pagination
                      background
                      layout="prev, pager, next"
                      :total="store.state.articleModule.total"
                      v-model:currentPage="current"
                      v-model:page-size="size"
                      @current-change="handleCurrentChange"
                  />
                </div>
              </div>
            </div>
            <div class="hcRight">
            </div>
          </div>
        </div>
        <div class="homeBottom">
          <div class="links">
            <div class="iconBox">
              <div class="icon">
                <ul>
                  <li>
                    <el-icon><Promotion /></el-icon>
                  </li>
                  <li>
                    <el-icon><CoffeeCup /></el-icon>
                  </li>
                  <li>
                    <el-icon><Promotion /></el-icon>
                  </li>
                  <li>
                    <el-icon><CoffeeCup /></el-icon>
                  </li>
                </ul>
              </div>
              <div class="Copyright">
                <span>Theme by YYX | Copyright © 2022-2023 本博客仅仅作为自己学习日常学习记录使用</span>
              </div>
            </div>
          </div>
        </div>
        <el-backtop target=".el-scrollbar__wrap"  :right="40" :bottom="40"></el-backtop>
      </el-scrollbar>
    </div>
  </div>
</template>

<script setup>
import headerNav from "@/components/headerNav";
import {Top,Promotion,CoffeeCup,UserFilled,Calendar,FolderOpened,Notebook} from '@element-plus/icons-vue'
import {onMounted, ref, reactive, onUpdated, onBeforeUpdate} from 'vue'
import $ from 'jquery'
import store from '@/store'
import {useRouter} from "vue-router"
let router = useRouter()
let current = ref(1)
let size = ref(5)
let elScroll = ref()
let isShowTopNav = false
let ImgArr = ["\"http://p1.music.126.net/yOGNQmrglFkbxBDmdM1BvQ==/109951168449395511.jpg\"","\"http://p1.music.126.net/Z_4opVhkqV6AUY5fUjTgVg==/109951168449860831.jpg\""]
let index = 0
let switchState = reactive({
  recommend:false,
  history:false,
  recent:true,
})
const switchToRecommend = () => {

  if (!switchState.recommend){
    $(".switchToRecommend").addClass("Strong")
    $(".switchToHistory").removeClass("Strong")
    $(".switchToRecent").removeClass("Strong")

    switchState.recommend = true
    switchState.history = false
    switchState.recent = false

    current.value = 1

    //发送请求
    store.dispatch("articleModule/getArticleListByRecommend",{current:current.value,size:size.value})
  }else {

  }
}
const switchToHistory = () => {
  if (!switchState.history){
    $(".switchToRecommend").removeClass("Strong")
    $(".switchToHistory").addClass("Strong")
    $(".switchToRecent").removeClass("Strong")

    switchState.recommend = false
    switchState.history = true
    switchState.recent = false
    current.value = 1
    //发送请求
    store.dispatch("articleModule/getArticleListByHistory",{current:current.value,size:size.value})
  }else {

  }
}
const switchToRecent = () => {
  if (!switchState.recent){
    $(".switchToRecommend").removeClass("Strong")
    $(".switchToHistory").removeClass("Strong")
    $(".switchToRecent").addClass("Strong")

    switchState.recommend = false
    switchState.history = false
    switchState.recent = true
    current.value = 1
    //发送请求
    store.dispatch("articleModule/getArticleList",{current:current.value,size:size.value})
  }else {

  }
}
const handleCurrentChange = () => {
  if (switchState.recommend){
    store.dispatch("articleModule/getArticleListByRecommend",{current:current.value,size:size.value})
  }else if(switchState.history){
    store.dispatch("articleModule/getArticleListByHistory",{current:current.value,size:size.value})
  }else if (switchState.recent){
    store.dispatch("articleModule/getArticleList",{current:current.value,size:size.value})
  }
}

const redirectArticleIndex = (e) => {
  store.commit('articleModule/updateArticleId_pre',e)
  store.dispatch('articleModule/getArticleCatalogue')
  router.push('/article')
}

const scroll = (e) => {
  if (e.scrollTop > 16 && !isShowTopNav){
    let color = "rgba(0, 0, 0, 0.8)"
    isShowTopNav = true
    $(".headerContainer").css("background-color",color)
  }else if(e.scrollTop <= 16 && isShowTopNav){
    let color = "rgba(0, 0, 0, 0.3)"
    isShowTopNav = false
    $(".headerContainer").css("background-color",color)
  }
}

//懒加载
//  IntersectionObserver API只能设置一次，一次可以监听多个元素，但是对于v-for 生产不同
//  的div，他们的唯一标识不同，不能实现所有div元素懒加载
//  解决方法为，把v-for div 的:key属性清除，但是不能保证之后的功能实现与之冲突
const observer = ref()
const articleDiv = ref()
const callBack = (entries)=>{
  entries.forEach( entry => {
    const divTarget = entry.target
    //元素与窗口出现交叉
    if (entry.isIntersecting){

      divTarget.setAttribute('style','opacity: 1;transform: translateY(10px)')
      // observer.unobserve(divTarget)
      console.log("触发")
    }else {
      divTarget.setAttribute('style','opacity: 0;transform: translateY(50px)')
    }
  })
}
onMounted(() => {
  // articleDiv.value = document.querySelectorAll(".articleCard")
  articleDiv.value = document.getElementsByClassName("articleCard")

  console.log("OnMounted")
  console.log(articleDiv.value)
  observer.value = new IntersectionObserver(callBack)
  for (let i = 0; i < articleDiv.value.length; i++) {
    observer.value.observe(articleDiv.value[i])
  }
})

//背景图轮换
onMounted(() => {
  // setInterval(()=>{
  //   let temp = "url(" + ImgArr[index] + ")"
  //   $(".homeBg").css("background-image",temp)
  //   if(index === 0){
  //     index = 1
  //   }else {
  //     index = 0
  //   }
  // },5000)
  $(".switchToRecent").addClass("Strong")
  store.dispatch("articleModule/getArticleList",{current:current.value,size:size.value})
})
</script>

<style scoped>
.homeContainer>>>.el-scrollbar__bar.is-vertical {
  width: 1rem;
}
.homeContainer>>>.el-scrollbar__thumb:hover {
  background-color: #3c99f6;
  opacity: .5;
}
.homeContainer>>>.el-scrollbar__thumb {
  border-radius: 1.6rem;
}
.homeContainer {
  width: 100%;
  height: 100vh;

  position: relative;
}

.homeBg {

  width: 100vw;
  height: calc(100vw / 2);
  background-image: url("../assets/bg.jpg");
  background-repeat: no-repeat;
  background-size: 100vw calc(100vw / 1.6);
  transition: 0.5s ease-in-out;
}
.homeBody {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.2);

}

.BgContentArea {
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  /*backdrop-filter: blur(2px);*/

  display: flex;

}
.bgLeft {
  /*background-color: rgba(255, 0, 0, 0.5);*/
  margin-top: 5rem;

  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}
.bgRight {
  /*background-color: rgba(0, 0, 255, 0.5);*/
  flex: 2;
  margin-top: 5rem;

}
.headImgArea {
  width: 90%;
  height: 70%;
  background-color: rgba(246, 245, 245, 0.8);
  border-radius: 4rem;
  position: absolute;
  top: 10%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.headImgArea:after{
  content: "";
  display: block;
  background-image: url("../assets/headimg.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  width: 25%;
  height: 25%;
  position: absolute;
  top: 0%;
  left: 50%;
  transform: translate(-50%,-50%);

  border-radius: 50%;
  border: rgba(0, 0, 0, 0.5) solid 1px;
}
.userDesc {
  width: 90%;
  height: 30%;
  font-size: 1vw;
  text-align: center;
  display: flex;
  align-items: center;
  color: black;
  font-weight: bold;
  margin: 0 auto;
  display: flex;
  justify-content: center;
}


/*********************************** 中部样式 *************************/
.homeContent {
  /*border-top-left-radius: 4rem;*/
  /*border-top-right-radius: 4rem;*/
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.homeContentLayout {
  background-color: rgba(246, 245, 245, 0.5);
  width: 90%;
  /*height: 150vh;*/
  margin-top: 3rem;
  margin-bottom: 3rem;
  border-radius: 1.6rem;

  display: flex;
  align-items: center;

}
.hcLeft {
  flex: 3;
  height: 100%;

  display: flex;
  align-items: center;
  flex-direction: column;
}
.sort {
  width: 100%;
  height: 4rem;
  /*background-color: gold;*/
}
.sort ul{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
}
.sort ul li{
  margin-left: 2rem;
  font-size: 1.2rem;
  height: calc(100% - 1px);

  display: flex;
  align-items: center;
}
.sort ul li:first-child {
  margin-right: 1rem;
}
.sort ul li:hover {
  border-bottom: black solid 1px;
  font-weight: bold;
}
.Strong {
  font-weight: bold;
}
.articleArea {
  width: 100%;
  height: calc(100% - 4rem);
  display: flex;
  flex-direction: column;
  align-items: center;
}
.articleCard {
  position: relative;
  width: 90%;
  height: 16rem;
  background-color: white;
  margin-bottom: 3rem;
  border-radius: .8rem;
  opacity: 0.5;
  display:flex;
  flex-direction: column;

  transform: translateY(50px);

  transition: 0.2s;
}
.articleCard:hover {
  box-shadow: 0 0 10px 5px rgba(0, 0, 0, 0.1);
}
.articleCard:first-child{
  margin-top: 2rem;
}
.articleCard:last-child{
  margin-bottom: 2rem;
}
.articleInfo {
  flex: 1;
  display: flex;
  justify-content: center;
  flex-direction: column;
}
.articleTitle {
  margin-left: 1rem;
  font-size: 1.6rem;
  margin-top: 2rem;
  cursor: pointer;
}
.articleTitle>span:hover {
  color: #3c99f6;
}
.articleOther {
  margin-left: 1rem;
  margin-right: 1rem;
  margin-top:  1rem;
  font-size: .9rem;
  height: 2rem;
  border-bottom: rgba(134, 134, 134, 0.5) solid 1px;
  display: flex;
  align-items: center;
}
.articleOther>span {
  margin-right: 1rem;
  cursor: pointer;
}
.articleOther>span:hover {
  color: #3c99f6;
}
.articleTxT{
  flex: 1;
}
.articleTxT>div {
  margin-left: 1rem;
  margin-top: 1rem;
  font-size: 1.1rem;

  overflow: hidden;
  text-overflow: ellipsis;
}
.articlePager {
  width: 100%;
  height: 2rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: center;
  align-items: center;
}

.hcRight {
  flex: 1;
  height: 50vh;
  background-color: #f8ba77;
}


/*************************** 底部样式 ********************/
.homeBottom {
  height: 30rem;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.links {
  width: 100%;
  height: 10rem;
  position: relative;
}
.links>.iconBox{
  width: 100%;
  height: 70%;


  position: absolute;
  bottom:0;
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  flex-direction: column;
  align-items: center;
}
.links>.iconBox>.icon {
  width: 50%;
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
.links>.iconBox>.Copyright {
  width: 80%;
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1rem;
}
.links>.iconBox ul{
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: 1.2rem;
  height: 100%;
  width: 25%;
}
.links>.iconBox ul li {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}



.homeBody>>>.el-backtop{
  --el-backtop-bg-color: #2c2c2c;
  --el-backtop-text-color: #FFFFFF;
  --el-backtop-hover-bg-color: #3c98f5;
  width: 3rem;
  height: 3rem;

}
</style>
