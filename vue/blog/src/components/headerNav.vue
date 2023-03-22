<template>
  <div class="headerContainer">
    <div class="left" @click="homeClick">
      <div class="title">yyx’s Blog</div>
      <div class="logo"></div>
    </div>
    <div class="right">
      <div class="searchBox">
        <div class="search-box">
          <el-input
              v-model="inputSearch"
              ref="inputRef"
              clearable
              class="w-50 m-2"
              placeholder="此刻你正在想什么呢?"
              :prefix-icon="Search"
              @focus="searchFocus"
              type="text"
          />
        </div>
      </div>
      <ul class="menuNav">
        <li class="search">
          搜索
          <ul>
            <li>
              hahahaha
            </li>
          </ul>
        </li>
        <li class="home">
          主页
        </li>
        <li class="article">
          文章
          <ul>
            <li>
              hahahaha
            </li>
            <li>
              hahahah
            </li>
          </ul>
        </li>
        <li class="login" @click="loginClick">
          后台
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from 'vue-router'
import $ from 'jquery'
import {Search} from '@element-plus/icons-vue'
import {ref} from 'vue'
let inputSearch = ref('')
const router = useRouter()
const loginClick = () => {
  router.push('login')
}
const homeClick = () => {
  router.push('home')
}
const searchFocus = () => {
  console.log("获取焦点")
}
</script>

<script>
export default {
  name: "headerNav"
}
</script>

<style scoped>
.headerContainer {
  height: 3.6rem;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  /*backdrop-filter: blur(2px);*/

  display: flex;
  align-items: center;
  justify-content: space-between;

  position: fixed;
  /*top: 0;*/
  /*left: 0;*/
  z-index: 10;
  transition: background-color 0.3s;
}
.headerContainer .left,
.headerContainer .right{
  margin: auto 1rem;
  height: 100%;
  text-align: center;
  font-family: 楷体;
  font-weight: bold;
  color: var(--headerNav-font-light-color);

  cursor: pointer;

}

.headerContainer .left {
  width: 20rem;
  display: flex;
  align-items: center;
  justify-content: center;

}
.headerContainer .left .title {
  font-size: 2rem;

}
.headerContainer .left .logo {
  margin-left: 1rem;
  width: 2.5rem;
  height: 2.5rem;
  background-image: url("../assets/logo.png");
  background-repeat: no-repeat;
  background-size: cover;
}


/************************* 搜索栏******************************/
.headerContainer .right {
  width: 36rem;
  margin-right: 3rem;

  display: flex;
  justify-content: center;
  align-items: center;
}
.headerContainer .right .searchBox {
  flex: 1;
  position: relative;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.headerContainer .right .searchBox>>>.el-input{
  height: 2.6rem;
  margin-top: 0.2rem;
  margin-bottom: 0.2rem;
}
.headerContainer .right .searchBox>>>.el-input__wrapper{
  border-radius: 30px;
}
.headerContainer .right .searchBox>>>.el-input .el-input__icon{
  color: #f56c6c;
  font-size: 1.2rem;
}
/************************* 菜单*********************************/
.headerContainer .right .menuNav{
  flex: 2;

  height: calc(100% - 1px);

  display: flex;
  align-items: center;
  justify-content: center;
}
.headerContainer .right .menuNav .search,
.headerContainer .right .menuNav .home,
.headerContainer .right .menuNav .article,
.headerContainer .right .menuNav .login{
  height: 100%;
  width: 2rem;
  flex: 1;
  line-height: 3.6rem;
  font-size: 1.2rem;



  background-color: transparent;
  border-bottom: var(--headerNav-font-light-color) solid 0px;
}

.headerContainer .right .menuNav>*:hover {
  border-bottom: var(--headerNav-font-light-color) solid 1px;

}


.headerContainer .right .menuNav .article ul li,
.headerContainer .right .menuNav .search ul li{
  /* 列表展开前状态 */
  display: block;
  height: 0;
  transition: 0.3s;
  opacity: 0;
  background-color: rgba(0, 0, 0, 0.25);
  transform: translateY(-.1rem);
}
.headerContainer .right .menuNav .article ul li:last-child,
.headerContainer .right .menuNav .search ul li:last-child{
  border-bottom-right-radius: .8rem;
  border-bottom-left-radius: .8rem;
}
.headerContainer .right .menuNav .article:hover li,
.headerContainer .right .menuNav .search:hover li{
  height: 6rem;
  opacity: 1;
  transform: translateY(-.1rem);
}


</style>