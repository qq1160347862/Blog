<template>
  <div class="articleControllerContainer">
    <div class="articleControllerCard">
      <div class="accBtnArea">
        <div class="Btn">
          <el-button color="#409dfe" round>新增文章</el-button>
          <el-button color="#67c13a" round>更新文章</el-button>
          <el-button color="#f46c6c" round>删除文章</el-button>
        </div>
        <div class="search">
          <el-input placeholder="请输入搜索内容"
                    v-model="inputSearch"
                    :prefix-icon="Search"
                    type="text"/>
          <el-button color="#e5a13c" round>查询文章</el-button>
        </div>
      </div>
      <div class="accFormArea"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "articleController"
}
</script>
<script setup>
import {Search} from "@element-plus/icons-vue";
import {ref} from 'vue'
let inputSearch = ref('')
</script>

<style scoped>
.articleControllerContainer {
  width: 100%;
  height: 100%;

  display: flex;
  justify-content: center;
  align-items: center;
}
.articleControllerCard {
  width: 95%;
  height: 95%;
  background-color: #FFFFFF;
  border-radius: 1rem;
  box-shadow: 0 0 10px 2px rgba(3, 3, 3, 0.3);

  display: flex;
  flex-direction: column;
}


/********************* 按钮区域样式 ******************/
.accBtnArea {
  flex: 1;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.accBtnArea>.Btn {
  /*height: 100%;*/
  margin-left: 1rem;
}
.accBtnArea>>>.el-button {
  color: #FFFFFF;
}
.accBtnArea>.search{
  /*height: 100%;*/
  width: 20rem;
  /*background-color: #f56c6c;*/
  margin-right: 1rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.accBtnArea>.search>>>.el-input{
  height: 2.6rem;
  margin-top: 0.2rem;
  margin-bottom: 0.2rem;
}
.accBtnArea>.search>>>.el-input__wrapper{
  border-radius: 30px;
}
.accBtnArea>.search>>>.el-input .el-input__icon{
  color: #f56c6c;
  font-size: 1.2rem;
}

/********************* 表格区域样式 ******************/
.accFormArea {
  flex: 8;
  background-color: rgba(248, 186, 119, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  color: #f46c6c;
}
</style>