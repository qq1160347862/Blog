<template>
  <div class="sortControllerContainer">
    <div class="sortControllerCard">
      <div class="stcBtnArea">
        <div class="Btn">
          <el-button color="#409dfe" round @click="openAddDrawer">新增分类</el-button>
          <el-button color="#f46c6c" round @click="deleteSorts">删除分类</el-button>
        </div>
        <div class="search">
          <el-input placeholder="请输入搜索内容"
                    v-model="inputSearch"
                    :prefix-icon="Search"
                    type="text"/>
          <el-button color="#e5a13c" round @click="searchSort">查询分类</el-button>
        </div>
      </div>
      <div class="stcFormArea">
        <el-table ref="stcTable"
                  :data="store.state.sortModule.sorts"
                  size="large"
                  :row-key="row => row.sortId"
                  @selection-change="SortSelection"
                  style="width: 96%;border-radius: 1rem">
          <!--          跨页多选需要给表格table的:row-key设置唯一标识，在type="selection" 后添加:reserve-selection="true"，即保存跨页的多选状态
                        适用场景：列表页导出、批量操作 不适用于有回显的（新增编辑详情同一组件情况需要特别处理）-->
          <el-table-column type="selection" :reserve-selection="true" width="55"/>
          <el-table-column property="sortId" label="分类ID"  />
          <el-table-column property="sortName" label="分类昵称"  />
          <el-table-column fixed="right" label="分类操作">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="openEditDrawer(scope.row)">修改</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="stcTablePager">
          <el-pagination
              background
              layout="prev, pager, next"
              :total="store.state.sortModule.total"
              v-model:currentPage="current"
              v-model:page-size="size"
              @current-change="handleCurrentChange"
          />
        </div>
      </div>
      <el-drawer v-model="addDrawer"
                 :with-header="false"
                 size="80%"
                 destroy-on-close
                 :direction="direction">
        <el-scrollbar>
          <div class="addDrawer">
            <div class="adDForm">
              <el-form :model="addFormDate"
                       size="large"
                       :rules="rules"
                       label-width="120px"
                       status-icon ref="addFormRef">
                <el-form-item prop="sortName" label="分类昵称">
                  <el-input v-model="addFormDate.sortName"/>
                </el-form-item>
              </el-form>
            </div>
            <div class="adDBtn">
              <el-button color="#409dfe" round @click="submit">新增分类</el-button>
            </div>
          </div>
        </el-scrollbar>
      </el-drawer>
      <el-drawer v-model="editDrawer"
                 :with-header="false"
                 size="80%"
                 destroy-on-close
                 :direction="direction">
        <el-scrollbar>
          <div class="editDrawer">
            <div class="etDForm">
              <el-form :model="editFormDate"
                       size="large"
                       :rules="rules"
                       label-width="120px"
                       status-icon ref="editFormRef">
                <el-form-item prop="sortName" label="分类昵称">
                  <el-input v-model="editFormDate.sortName"/>
                </el-form-item>
              </el-form>
            </div>
            <div class="etDBtn">
              <el-button color="#67c03a" round @click="edit">修改分类</el-button>
            </div>
          </div>
        </el-scrollbar>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "sortController"
}
</script>
<script setup>
import {reactive, ref} from "vue";
import {Search} from "@element-plus/icons-vue";
import store from "@/store";
import {addSort,updateSort,deleteSort} from "@/request/api/sort";

let current = ref(1)
let size = ref(10)
let inputSearch = ref('')
let addDrawer = ref(false)
let editDrawer = ref(false)
let direction = ref('ttb')
let addFormDate = reactive({
  sortName:'',
})
let editFormDate = reactive({
  sortId:'',
  sortName:'',
})
let addFormRef = ref()
let editFormRef = ref()
let stcTable = ref()
let rules = reactive({
  sortName:[
    { required: true, message: '请输入分类昵称', trigger:'blur'},
  ],
})
let sortIdList = []

const openAddDrawer = () => {
  addDrawer.value = true
}
const openEditDrawer = (e) => {
  editFormDate = reactive({
    sortId:e.sortId,
    sortName:e.sortName,
  })
  editDrawer.value = true
}
const deleteSorts = async () => {
  let res = await deleteSort(sortIdList)
  //重新获取表格信息
  await store.dispatch("sortModule/getSortByPage",{current:current.value,size:size.value})
  console.log(res)
}
const handleCurrentChange = () => {
  store.dispatch("sortModule/getSortByPage",{current:current.value,size:size.value})
}
const SortSelection = () => {
  let tempList = []
  for (let i = 0; i < stcTable.value.getSelectionRows().length; i++){
    tempList.push(stcTable.value.getSelectionRows()[i].sortId)
  }
  sortIdList = tempList
}

const submit = async () => {
  await addFormRef.value.validate(async (value) => {
    if (value){
      //添加请求
      let res = await addSort(addFormDate)
      console.log(res)
      addDrawer.value = false
      //初始化表单
      addFormDate = reactive({
        sortName:'',
      })
      //重新获取表格信息
      await store.dispatch("sortModule/getSortByPage",{current:current.value,size:size.value})
    }else {
      console.log("表单不完整")
    }
  })
}
const edit = async () => {
  await editFormRef.value.validate(async (value) => {
    if (value){
      //更新请求
      let res = await updateSort(editFormDate)
      console.log(res)
      editDrawer.value = false
      //重新获取表格信息
      await store.dispatch("sortModule/getSortByPage",{current:current.value,size:size.value})
    }else {
      console.log("表单不完整")
    }
  })
}
const searchSort = async () => {
  current.value = 1
  size.value = 10
  if (inputSearch.value === undefined || inputSearch.value === ""){
    await store.dispatch("sortModule/getSortByPage",{current:current.value,size:size.value})
  }else {
    await store.dispatch("sortModule/likeSortByPage",{current:current.value,size:size.value,query:inputSearch.value})
  }
}

//获取表格信息
store.dispatch("sortModule/getSortByPage",{current:current.value,size:size.value})
</script>

<style scoped>
.sortControllerContainer {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.sortControllerCard {
  width: 95%;
  height: 95%;
  background-color: #eaeaef;
  border-radius: 1rem;
  box-shadow: 0 0 10px 2px rgba(227, 228, 240, 0.24);

  display: flex;
  flex-direction: column;
}

/********************* 按钮区域样式 ******************/
.stcBtnArea {
  flex: 1;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.stcBtnArea>.Btn {
  /*height: 100%;*/
  margin-left: 1rem;
}
.stcBtnArea>>>.el-button {
  color: #FFFFFF;
}
.stcBtnArea>.search{
  /*height: 100%;*/
  width: 20rem;
  /*background-color: #f56c6c;*/
  margin-right: 1rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.stcBtnArea>.search>>>.el-input{
  height: 2.6rem;
  margin-top: 0.2rem;
  margin-bottom: 0.2rem;
}
.stcBtnArea>.search>>>.el-input__wrapper{
  border-radius: 30px;
}
.stcBtnArea>.search>>>.el-input .el-input__icon{
  color: #f56c6c;
  font-size: 1.2rem;
}

/********************* 表格区域样式 ******************/
.stcFormArea {
  flex: 8;
  /*background-color: rgba(197, 148, 75, 0.91);*/
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  padding-top: 2rem;
}
.stcTablePager {
  margin-bottom: 1rem;
}

/********************* 抽屉区域样式 ******************/
.sortControllerContainer>>>.el-scrollbar__bar.is-vertical{
  width: 0;
}
.sortControllerCard>>>.el-overlay{
  background-color: transparent;
}
.sortControllerCard>>>.el-drawer.ttb {
  width: 50vw;
  top:50%;
  left: 50%;
  transform: translate(-50%,-50%);
  border-radius:1rem;
}
.sortControllerCard>>>.el-drawer__body{
  padding: 0;
}
.addDrawer,
.editDrawer{
  width: 100%;
  height: 100%;

  display: flex;
  flex-direction: column;
  align-items: center;
}
.adDForm,.etDForm {
  margin-top: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.adDBtn,.etDBtn {
  margin-top: 2rem;
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
}
.adDBtn>>>.el-button,
.etDBtn>>>.el-button{
  color: #FFFFFF;
}
</style>