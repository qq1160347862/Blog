<template>
  <div class="articleControllerContainer">
    <div class="articleControllerCard">
      <div class="accBtnArea">
        <div class="Btn">
          <el-button color="#409dfe" round @click="openAddDrawer">新增文章</el-button>
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
      <div class="accFormArea">
        <el-table ref="accTable"
                  :data="store.state.articleModule.article"
                  size="large"
                  @selection-change="testSelection"
                  style="width: 96%;border-radius: 1rem">
          <el-table-column type="selection" width="55"/>
          <el-table-column property="articleId" label="文章ID" width="80"/>
          <el-table-column property="title" label="文章标题"  />
          <el-table-column property="userId" label="用户ID"  width="80"/>
          <el-table-column property="labelId" label="标签ID"  width="80"/>
          <el-table-column property="recommend" label="推荐指数"  width="100"/>
          <el-table-column property="pubDate" label="发行日期"  />
          <el-table-column fixed="right" label="文章操作" width="120">
            <template #default>
              <el-button link type="primary" size="small">预览</el-button>
              <el-button link type="primary" size="small">修改</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="accTablePager">
          <el-pagination
              background
              layout="prev, pager, next"
              :total="store.state.articleModule.articleTotal"
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
                <el-form-item prop="title" label="文章标题">
                  <el-input v-model="addFormDate.title"/>
                </el-form-item>
                <el-form-item prop="userId" label="用户ID">
                  <el-select
                      placeholder="请选择用户"
                      v-model="addFormDate.userId">
                    <el-option
                        v-for="item in userList"
                        :key="item.userId"
                        :value="item.userId"
                        :label="item.userName"/>
                  </el-select>
                </el-form-item>
                <el-form-item prop="labelId" label="标签ID">
                  <el-select
                      placeholder="请选择用户"
                      v-model="addFormDate.labelId">
                    <el-option
                        v-for="item in labelList"
                        :key="item.labelId"
                        :value="item.labelId"
                        :label="item.labelName"/>
                  </el-select>
                </el-form-item>
                <el-form-item prop="sortId" label="分类ID">
                  <el-select
                      placeholder="请选择用户"
                      v-model="addFormDate.sortId">
                    <el-option
                        v-for="item in sortList"
                        :key="item.sortId"
                        :value="item.sortId"
                        :label="item.sortName"/>
                  </el-select>
                </el-form-item>
                <el-form-item prop="recommend" label="推荐指数">
                  <el-rate v-model="addFormDate.recommend"
                           :colors="['#409eff', '#67c03a', '#f36c6c']"
                           :texts="['不推荐','差强人意','一般','很不错','十分推荐']"
                           show-text
                           clearable/>
                </el-form-item>
                <el-form-item prop="pubDate" label="发行日期">
                  <el-date-picker v-model="addFormDate.pubDate"
                                  type="date"
                                  label="Pick a date"
                                  placeholder="Pick a date"
                                  style="width: 100%"/>
                </el-form-item>
              </el-form>
              <div class="editArea" style="border: 1px solid #ccc">
                <Toolbar
                    style="border-bottom: 1px solid #ccc"
                    :editor="editorRef"
                    :defaultConfig="toolbarConfig"
                    :mode=mode
                />
                <Editor
                    style="height: 676px; overflow-y: hidden;"
                    v-model="valueHtml"
                    :defaultConfig="editorConfig"
                    :mode=mode
                    @onCreated="handleCreated"
                />
              </div>
            </div>
            <div class="adDBtn">
              <el-button color="#409dfe" round @click="submit">新增文章</el-button>
            </div>
          </div>
        </el-scrollbar>

      </el-drawer>
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
import {onMounted, reactive, ref, shallowRef, onBeforeUnmount} from 'vue'
import store from "@/store";
import '@wangeditor/editor/dist/css/style.css'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import xmlFormat from 'xml-formatter';
import {addArticle} from "@/request/api/article";
let current = ref(1)
let size = ref(10)
let inputSearch = ref('')
let accTable = ref()
let addDrawer = ref(false)
let direction = ref('ttb')
let addFormRef = ref()
let addFormDate = reactive({
  userId:'',
  labelId:'',
  sortId:'',
  recommend:ref(),
  title:'',
  pubDate:'',
  content:'',
})
let rules = reactive({
  userId:[
    { required: true, message: '请输入用户ID', trigger:'blur'},
  ],
  labelId:[
    { required: true, message: '请输入标签ID', trigger:'blur'},
  ],
  sortId:[
    { required: true, message: '请输入分类ID', trigger:'blur'},
  ],
  recommend:[
    { required: true, message: '请输入推荐指数', trigger:'blur'},
  ],
  title:[
    { required: true, message: '请输入文章标题', trigger:'blur'},
  ],
  pubDate:[
    { required: true, message: '请输入发行日期', trigger:'blur'},
  ],
  content:[
    { required: true, message: '请输入内容', trigger:'blur'},
  ]
})
let userList = reactive([])
let labelList = reactive([])
let sortList = reactive([])
let mode = 'default'
//编辑器实例
const editorRef = shallowRef()
//内容Html
const valueHtml = ref('<p>hello</>')
// 模拟 ajax 异步获取内容
onMounted(() => {
  setTimeout(() => {
    valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
  }, 1500)
})
const toolbarConfig = {}
const editorConfig = { placeholder: '请输入内容...',MENU_CONF:{}}
editorConfig.MENU_CONF['uploadImage'] = {
  server: 'http://localhost:8086/upload',
  fieldName: 'photo',   //和后端请求传参名称一致
  customInsert(res, insertFn){
    if (res.code === 2000){
      let url = 'http://localhost:8086/upload/'
      insertFn(url+res.data.imageName, res.data.imageName, url+res.data.imageName)
    }else {
      console.log("图片上传失败")
    }
  },
}
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}

const handleCurrentChange = () => {
  store.dispatch("articleModule/getArticleByPage",{current:current.value,size:size.value})
}
const openAddDrawer = () => {
  addDrawer.value = true
  userList = store.state.userModule.userList
  labelList = store.state.LabelModule.labelList
  sortList = store.state.sortModule.sortList
}
const testSelection = () => {
  console.log("123")
}
const submit = async () => {
  await addFormRef.value.validate(async (value) => {
    if (value){
      const editor = editorRef.value
      addFormDate.content = xmlFormat(editor.getHtml())
      let res = await addArticle(addFormDate)
      console.log(res)
      addDrawer.value = false

      //初始化表单
      addFormDate = reactive({
        userId:'',
        labelId:'',
        sortId:'',
        recommend:ref(),
        title:'',
        pubDate:'',
        content:'',
      })
    }else {
      console.log("表单不完整")
    }
  })
}
//获取表格信息,用户信息等
store.dispatch("articleModule/getArticleByPage",{current:current.value,size:size.value})
store.dispatch("userModule/UserIdAndUserName")
store.dispatch("LabelModule/LabelIdAndLabelName")
store.dispatch("sortModule/SortIdAndSortName")

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
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  padding-top: 2rem;
}
.accTablePager {
  margin-bottom: 1rem;
}

/********************* 抽屉区域样式 ******************/
.articleControllerContainer>>>.el-scrollbar__bar.is-vertical{
  width: 0;
}
.articleControllerCard>>>.el-overlay{
  background-color: transparent;
}
.articleControllerCard>>>.el-drawer.ttb {
  width: 50vw;
  top:50%;
  left: 50%;
  transform: translate(-50%,-50%);
  border-radius:1rem;
}
.articleControllerCard>>>.el-drawer__body{
  padding: 0;
}
.addDrawer {
  width: 100%;
  height: 100%;

  display: flex;
  flex-direction: column;
  align-items: center;
}
.adDForm {
  margin-top: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.adDBtn {
  margin-top: 2rem;
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
}
.adDBtn>>>.el-button {
  color: #FFFFFF;
}
.editArea {
  height: 800px;
  width: 90%;
}
</style>
