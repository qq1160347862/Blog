<template>
  <div class="articleContainer">
    <headerNav></headerNav>
    <div class="articleArea">
      <el-scrollbar @scroll="scroll" ref="elScroll">
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
import '@wangeditor/editor/dist/css/style.css'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import headerNav from "@/components/headerNav";
import {Back,Right,Promotion,CoffeeCup,UserFilled,Calendar,FolderOpened,Notebook} from '@element-plus/icons-vue'
import store from '@/store'
import $ from "jquery";
import {onBeforeUpdate, onMounted, onUpdated, ref, shallowRef} from "vue";
let elScroll = ref()
let isShowTopNav = false
const editorRef = shallowRef()
let mode = 'default'
const valueHtml = ref('')
const editorConfig = { placeholder: '请输入内容...',MENU_CONF:{}}
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
  setTimeout(()=>{
    editorRef.value.setHtml(store.state.articleModule.article_pre.content)
  },500)

  editor.disable()
  // editor.fullScreen()
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
const updateArticlePre = async (id) => {
  await store.dispatch('articleModule/getArticleById',id)   //修改当前页文章
  editorRef.value.setHtml(store.state.articleModule.article_pre.content)
}

//获取文章
store.dispatch('articleModule/getArticleById',store.state.articleModule.articleId_pre)
</script>

<style scoped>
.articleArea{
  height: 100vh;
  background-color: #3c99f6;
}
.articleCardContainer {
  width: 100vw;
  padding-top: 3.6rem;
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
  background-color: rgba(255, 255, 255, 1);
  box-shadow: 0 0 25px 1px #2c2c2c;

  padding: 1rem 1rem;
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
</style>