<template>
  <div class="articleContainer">
    <headerNav></headerNav>
    <div class="articleArea">
      <el-scrollbar @scroll="scroll" ref="elScroll">
        <div class="articleAreaLayout">
          <div class="articleCatalogue">
            <div class="Catalogue">目录</div>
            <div class="articleCatalogueIsOpen" @click="CatalogueIsOpen">
              <el-icon v-show="!isOpen"><Folder /></el-icon>
              <el-icon v-show="isOpen"><FolderOpened /></el-icon>
            </div>
            <el-scrollbar>
              <div class="CatalogueItem" v-for="(item,index) in store.state.sortModule.sortList">
                <span  @click="SortIsOpen(index)">
                  <el-icon v-show="!isOpen_sort[index]"><CaretRight /></el-icon>
                  <el-icon v-show="isOpen_sort[index]"><CaretBottom /></el-icon>
                  {{item.sortName}}
                </span>
                <div :class="'CatalogueItemArticle'+index"
                     id="CatalogueItemArticle"
                     v-show="store.state.articleModule.articleSortCatalogue[index] !== null"
                     v-for="(item2,index2) in store.state.articleModule.articleSortCatalogue[index]"
                     @click="updateArticlePre(item2.articleId)">
                  <span>{{item2.title}}</span>
                </div>
                <div :class="'CatalogueItemArticle'+index" id="CatalogueItemArticle" v-show="store.state.articleModule.articleSortCatalogue[index] === null">
                  <span>{{"没有此类别文章"}}</span>
                </div>
              </div>
            </el-scrollbar>
          </div>
          <div class="articleCardContainer">
            <div class="articlesCard">
              <div class="articleCardTop">
                <!--              {{store.state.articleModule.articleList[articleIndex].title}}-->
                <span>
                <el-icon><Notebook/></el-icon>
                {{store.state.articleModule.article_pre.labelName}}
              </span>
                <span>
                <el-icon><FolderOpened/></el-icon>
                {{store.state.articleModule.article_pre.sortName}}
              </span>
                <span>
                <el-icon><Calendar/></el-icon>
                {{store.state.articleModule.article_pre.pubDate}}
              </span>
                <span>
                <el-icon><UserFilled/></el-icon>
                {{store.state.articleModule.article_pre.userName}}
              </span>

              </div>
              <div class="articleCardContent">
                <div class="articlesTitle">
                  {{store.state.articleModule.article_pre.title}}
                </div>
                <div class="articleContent">
                  <Editor
                      style="overflow-y: hidden;"
                      v-model="valueHtml"
                      :defaultConfig="editorConfig"
                      :mode=mode
                      @onCreated="handleCreated"
                  />
                </div>
              </div>
              <el-divider content-position="center">文章结束了</el-divider>
              <div class="articleCardBottom">
                <div class="lastArticle">
                  <el-icon><Back/></el-icon>
                  <span @click="updateArticlePre(store.state.articleModule.articleId_last.id)">
                  {{store.state.articleModule.articleCatalogue[store.state.articleModule.articleId_last.index].title}}
                </span>
                </div>
                <div class="nextArticle">
                <span @click="updateArticlePre(store.state.articleModule.articleId_next.id)">
                  {{store.state.articleModule.articleCatalogue[store.state.articleModule.articleId_next.index].title}}
                </span>
                  <el-icon><Right/></el-icon>
                </div>
              </div>
            </div>
            <div class="articleCardContainerBottom">
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
          </div>
          <transition name="rec">
            <div class="articleContentCatalogue" v-if="isShow">
              <catalogue :headers="headers"
                         :scrollContinerDom="scrollDom"
                         :scrollToFirstHeader="0"
                         :clickDistance="0"
                         :listId="activeTitle"
                         class="catalogue"></catalogue>
            </div>
          </transition>
        </div>
        <el-backtop target=".el-scrollbar__wrap"  :right="40" :bottom="40"></el-backtop>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
export default {
  name: "index"
}
</script>
<script setup>
import catalogue from 'wangeditor-catalogue'
import 'wangeditor-catalogue/css'
import '@wangeditor/editor/dist/css/style.css'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import headerNav from "@/components/headerNav";
import {CaretRight,CaretBottom,Folder,Back,Right,Promotion,CoffeeCup,UserFilled,Calendar,FolderOpened,Notebook} from '@element-plus/icons-vue'
import store from '@/store'
import $ from "jquery";
import {onBeforeUpdate, onMounted, onUpdated, reactive, ref, shallowRef} from "vue";
let elScroll = ref()
let isShowTopNav = false
const editorRef = shallowRef()
let mode = 'default'
let isOpen = ref(false)
let isOpen_sort = reactive([])
for (let i = 0; i < store.state.sortModule.sortList.length; i++) {
  isOpen_sort.push(false)
}

const valueHtml = ref('')
const editorConfig = { placeholder: '请输入内容...',MENU_CONF:{}}
const headers = ref()
let isShow = ref(false)
const scrollDom = ref()
let activeTitle = ref("")
//获取挂载后的组件
onMounted(()=>{
  scrollDom.value = document.getElementsByClassName(`el-scrollbar__wrap`).item(0)
})
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
  setTimeout(()=>{
    editorRef.value.setHtml(store.state.articleModule.article_pre.content)
    //获取文章标题H1、H2...
    headers.value = editor.getElemsByTypePrefix("header")
    if (headers.value.length > 0){
      isShow.value = true
    }else {
      isShow.value = false
    }
  },500)
  editor.disable()
  // editor.fullScreen()
}

const CatalogueIsOpen = () => {
  if (!isOpen.value){
    $(".articleCatalogue").toggleClass("active")
    $(".articleCatalogueIsOpen").toggleClass("active")
    $(".Catalogue").toggleClass("active")
    $(".CatalogueItem").toggleClass("active")
    // $(".CatalogueItemArticle").toggleClass("active")
  }else {
    $(".articleCatalogue").toggleClass("active")
    $(".articleCatalogueIsOpen").toggleClass("active")
    $(".Catalogue").toggleClass("active")
    $(".CatalogueItem").toggleClass("active")
    // $(".CatalogueItemArticle").toggleClass("active")
  }
  isOpen.value = !isOpen.value
}
const SortIsOpen = (index) => {
  if (!isOpen_sort[index]){
    $(".CatalogueItemArticle"+index).toggleClass("active")
  }else {
    $(".CatalogueItemArticle"+index).toggleClass("active")
  }
  isOpen_sort[index] = !isOpen_sort[index]
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

  //设置动态跟随效果
  let doe = document.getElementsByClassName(`el-scrollbar__wrap`).item(0)
  const list = $(`.cata_list`);
  /* 遍历每一个标题，比较标题距离顶部的距离和滚动条滚动的距离
  如果遍历到第一个大于滚动条滚动的距离，那么该标题就是活跃标题 */
  for (let i = 0; i < list.length; i++) {
    const curHead = document.querySelector(`#${list[i].id}`);
    if (curHead.offsetTop + 0 > doe.scrollTop && i > 0) {
      // list[i - 1].setAttribute("style","color: #0080ff;\n" +
      //     "       border-left: 2px solid #0080ff;transition: border-left 0.3s;")
      // for (let j = 0; j < list.length; j++) {
      //   if (j != (i - 1)){
      //     list[j].setAttribute("style","color: #000000;\n" +
      //         "       border-left: 2px solid #e0e0e0;transition: border-left 0.3s;")
      //   }
      // }
      activeTitle.value = list[i - 1].id;
      break;
    }
  }
}
const updateArticlePre = async (id) => {
  await store.dispatch('articleModule/getArticleById',id)   //修改当前页文章
  editorRef.value.setHtml(store.state.articleModule.article_pre.content)
  headers.value = editorRef.value.getElemsByTypePrefix("header")
  if (headers.value.length > 0){
    isShow.value = true
  }else {
    isShow.value = false
  }
  elScroll.value.scrollTo(0,0)
}

//获取文章以及分类目录
store.dispatch('articleModule/getArticleById',store.state.articleModule.articleId_pre)
store.dispatch('sortModule/SortIdAndSortName')
store.dispatch('articleModule/getArticleAndSort')
</script>

<style scoped>
.articleArea{
  height: 100vh;
  /*background-color: #f56c6c;*/
  background-image: url("../../assets/bg.jpg");
  background-size: cover;
  background-repeat: no-repeat;
}

.articleCardContainer {
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.articleCardContainerBottom{
  height: 30rem;
  width: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.articlesCard {
  width: 50%;
  margin-top: 1.6rem;
  background-color: rgba(255, 255, 255, 0.7);
  box-shadow: 0 0 25px 1px #2c2c2c;
  backdrop-filter: blur(10px);
  border-radius: .6rem;
  padding: 1rem 1rem;
}
.articlesCard>>>.el-divider__text{
  background-color: transparent;
}
.articleCardTop{
  height: 2rem;
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
}
.articleCardTop>span {
  margin-right: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}
.articleCardTop>span:hover{
  color: #3c99f6;
}
.articleCardContent{
  /*height: calc(100% - 7rem);*/
}
.articlesTitle {
  padding-top: 1rem;
  padding-left: 1rem;
  font-size: 1.6rem;
  font-weight: bold;
}
.articleContent {
  margin-top: 2rem;
}
.articleCardBottom{
  height: 4rem;

  display: flex;
  justify-content: space-between;
}
.lastArticle,
.nextArticle{
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.lastArticle>span:hover,
.nextArticle>span:hover{
  color: #3c99f6;
}

.articleAreaLayout{
  padding-top: 3.6rem;
  display: flex;
  flex-direction: row;
  position: relative;
}
.articleCatalogue{
  width: 0;
  height: 100vh;
  background-color: #FFFFFF;
  position: fixed;
  transition: 0.3s;
  border-top-right-radius: .3rem;
  border-bottom-right-radius: .3rem;
}
.active.articleCatalogue{
  width: 15rem;
  height: 100vh;
  background-color: rgba(255, 255, 255, 0.7);
  backdrop-filter: blur(10px);
  position: fixed;
}
.Catalogue{
  position: absolute;
  top: 25px;
  left: 50%;
  font-size: 1.6rem;
  transform: translate(-50%,-50%);
  opacity: 0;
  transition: 0.1s;
}
.active.Catalogue{
  opacity: 1;
  transition: 0.3s 0.9s;
}
.articleCatalogueIsOpen{
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.6rem;
  width: 50px;
  height: 50px;
  background-color: rgba(255, 255, 255, 0.7);
  border-radius: 50%;
  transform: translateX(0px);
  transition: 0.3s;

}
.active.articleCatalogueIsOpen{
  width: 50px;
  height: 50px;
  background-color: transparent;
  transform: translateX(calc(14rem - 50px));
  transition: border-radius 0.3s,transform 0.6s 0.3s;
}
.articleCatalogueIsOpen:hover {
  background-color: #2c2c2c;
  color: #FFFFFF;
}
.active.articleCatalogueIsOpen:hover{
  background-color: #2c2c2c;
  color: #FFFFFF;
}
.CatalogueItem {
  margin-bottom: 1rem;
  font-size: 1.2rem;
  opacity: 0;
  transition: 0.1s;
  cursor: auto;
}
.active.CatalogueItem{
  display: block;
  opacity: 1;
  transition: 0.3s 0.9s;
  cursor: pointer;
}
.CatalogueItem>span{
  margin-left: 1rem;
  cursor: pointer;
  display: flex;
  align-items: center;
}
#CatalogueItemArticle{
  font-size: 1rem;
  padding-left: 1.5rem;
  height: 0;
  display: flex;
  align-items: center;
  transition: 0.3s 0.3s;
}
#CatalogueItemArticle span {
  opacity: 0;
  transition: 0.3s,transform 0s 0.3s;
  cursor: default;
  transform: translateX(-500px);
}
#CatalogueItemArticle:hover {
  color: #FFFFFF;
  cursor: pointer;
  background-color: rgb(44, 44, 44);
}
.active#CatalogueItemArticle {
  height: 2rem;

  transition: 0.3s;
}
.active#CatalogueItemArticle span {
  opacity: 1;
  cursor: pointer;
  transition: 0.3s 0.3s,transform 0s,color 0s;
  transform: translateX(0);
}
.articleCatalogue>>>.el-scrollbar__bar.is-vertical{
  width: 0;
}

.articleContentCatalogue {
  width: 16rem;
  margin-top: 1.6rem;
  background-color: rgba(255, 255, 255, 0.7);
  position: fixed;
  right: 3rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: .8rem;
  box-shadow: 0 0 8px 2px #FFFFFF;
  backdrop-filter: blur(10px);

}
/*************************** 底部样式 ********************/
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
  color: #FFFFFF;
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
  width: 50%;
}
.links>.iconBox ul li {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}


.articleArea>>>.el-backtop{
  --el-backtop-bg-color: #2c2c2c;
  --el-backtop-text-color: #FFFFFF;
  --el-backtop-hover-bg-color: #3c98f5;
  width: 3rem;
  height: 3rem;

}


/*************************** vue组件动画 ********************/
.rec-enter-active{
  animation: articleContentCatalogue .5s ease-in-out;
}
.rec-leave-active{
  animation: articleContentCatalogue .5s ease-in-out reverse;
}
@keyframes articleContentCatalogue {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>