<template>
  <div class="labelControllerContainer">
    <div class="labelControllerCard">
      <div class="llcBtnArea">
        <div class="Btn">
          <el-button color="#409dfe" round @click="openAddDrawer">新增标签</el-button>
          <el-button color="#f46c6c" round @click="deleteLabels">删除标签</el-button>
        </div>
        <div class="search">
          <el-input placeholder="请输入搜索内容"
                    v-model="inputSearch"
                    :prefix-icon="Search"
                    type="text"/>
          <el-button color="#e5a13c" round>查询标签</el-button>
        </div>
      </div>
      <div class="llcFormArea">
        <el-table ref="llcTable"
                  :data="store.state.LabelModule.labels"
                  size="large"
                  :row-key="row => row.labelId"
                  @selection-change="LabelSelection"
                  style="width: 96%;border-radius: 1rem">
          <!--          跨页多选需要给表格table的:row-key设置唯一标识，在type="selection" 后添加:reserve-selection="true"，即保存跨页的多选状态
                        适用场景：列表页导出、批量操作 不适用于有回显的（新增编辑详情同一组件情况需要特别处理）-->
          <el-table-column type="selection" :reserve-selection="true" width="55"/>
          <el-table-column property="labelId" label="标签ID"  />
          <el-table-column property="labelName" label="标签昵称"  />
          <el-table-column fixed="right" label="标签操作">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="openEditDrawer(scope.row)">修改</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="llcTablePager">
          <el-pagination
              background
              layout="prev, pager, next"
              :total="store.state.LabelModule.total"
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
                <el-form-item prop="labelName" label="标签昵称">
                  <el-input v-model="addFormDate.labelName"/>
                </el-form-item>
              </el-form>
            </div>
            <div class="adDBtn">
              <el-button color="#409dfe" round @click="submit">新增标签</el-button>
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
                <el-form-item prop="labelName" label="标签昵称">
                  <el-input v-model="editFormDate.labelName"/>
                </el-form-item>
              </el-form>
            </div>
            <div class="etDBtn">
              <el-button color="#67c03a" round @click="edit">修改标签</el-button>
            </div>
          </div>
        </el-scrollbar>
      </el-drawer>
    </div>
  </div>
</template>

<script>
export default {
  name: "labelController"
}
</script>
<script setup>
import {reactive, ref} from "vue";
import {Search} from "@element-plus/icons-vue";
import store from "@/store";
import {addLabel, updateLabel, deleteLabel} from "@/request/api/label"

let current = ref(1)
let size = ref(10)
let inputSearch = ref('')
let addDrawer = ref(false)
let editDrawer = ref(false)
let direction = ref('ttb')
let addFormDate = reactive({
  labelName:'',
})
let editFormDate = reactive({
  labelId:'',
  labelName:'',
})
let addFormRef = ref()
let editFormRef = ref()
let llcTable = ref()
let rules = reactive({
  labelName:[
    { required: true, message: '请输入标签昵称', trigger:'blur'},
  ],
})
let labelIdList = []

const openAddDrawer = () => {
  addDrawer.value = true
}
const openEditDrawer = (e) => {
  editFormDate = reactive({
    labelId:e.labelId,
    labelName:e.labelName,
  })
  editDrawer.value = true
}
const deleteLabels = async () => {
  let res = await deleteLabel(labelIdList)
  //重新获取表格信息
  await store.dispatch("LabelModule/getLabelByPage",{current:current.value,size:size.value})
  console.log(res)
}
const handleCurrentChange = () => {
  store.dispatch("LabelModule/getLabelByPage",{current:current.value,size:size.value})
}
const LabelSelection = () => {
  let tempList = []
  for (let i = 0; i < llcTable.value.getSelectionRows().length; i++){
    tempList.push(llcTable.value.getSelectionRows()[i].labelId)
  }
  labelIdList = tempList
}

const submit = async () => {
  await addFormRef.value.validate(async (value) => {
    if (value){
      //添加请求
      let res = await addLabel(addFormDate)
      console.log(res)
      addDrawer.value = false
      //初始化表单
      addFormDate = reactive({
        labelName:'',
      })
      //重新获取表格信息
      await store.dispatch("LabelModule/getLabelByPage",{current:current.value,size:size.value})
    }else {
      console.log("表单不完整")
    }
  })
}
const edit = async () => {
  await editFormRef.value.validate(async (value) => {
    if (value){
      //更新请求
      let res = await updateLabel(editFormDate)
      console.log(res)
      editDrawer.value = false
      //重新获取表格信息
      await store.dispatch("LabelModule/getLabelByPage",{current:current.value,size:size.value})
    }else {
      console.log("表单不完整")
    }
  })
}

//获取表格信息
store.dispatch("LabelModule/getLabelByPage",{current:current.value,size:size.value})
</script>

<style scoped>
.labelControllerContainer {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.labelControllerCard {
  width: 95%;
  height: 95%;
  background-color: #eaeaef;
  border-radius: 1rem;
  box-shadow: 0 0 10px 2px rgba(227, 228, 240, 0.24);

  display: flex;
  flex-direction: column;
}

/********************* 按钮区域样式 ******************/
.llcBtnArea {
  flex: 1;

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.llcBtnArea>.Btn {
  /*height: 100%;*/
  margin-left: 1rem;
}
.llcBtnArea>>>.el-button {
  color: #FFFFFF;
}
.llcBtnArea>.search{
  /*height: 100%;*/
  width: 20rem;
  /*background-color: #f56c6c;*/
  margin-right: 1rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}
.llcBtnArea>.search>>>.el-input{
  height: 2.6rem;
  margin-top: 0.2rem;
  margin-bottom: 0.2rem;
}
.llcBtnArea>.search>>>.el-input__wrapper{
  border-radius: 30px;
}
.llcBtnArea>.search>>>.el-input .el-input__icon{
  color: #f56c6c;
  font-size: 1.2rem;
}

/********************* 表格区域样式 ******************/
.llcFormArea {
  flex: 8;
  /*background-color: rgba(197, 148, 75, 0.91);*/
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  padding-top: 2rem;
}
.llcTablePager {
  margin-bottom: 1rem;
}

/********************* 抽屉区域样式 ******************/
.labelControllerContainer>>>.el-scrollbar__bar.is-vertical{
  width: 0;
}
.labelControllerCard>>>.el-overlay{
  background-color: transparent;
}
.labelControllerCard>>>.el-drawer.ttb {
  width: 50vw;
  top:50%;
  left: 50%;
  transform: translate(-50%,-50%);
  border-radius:1rem;
}
.labelControllerCard>>>.el-drawer__body{
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