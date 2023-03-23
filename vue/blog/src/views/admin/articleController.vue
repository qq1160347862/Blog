<template>
  <div class="articleControllerContainer">
    <div class="articleControllerCard">
      <div class="accBtnArea">
        <div class="Btn">
          <el-button color="#409dfe" round @click="openAddDrawer">新增文章</el-button>
          <el-button color="#f46c6c" round @click="deleteArticles">删除文章</el-button>
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
                  :row-key="row => row.articleId"
                  @selection-change="ArticleSelection"
                  style="width: 96%;border-radius: 1rem">
<!--          跨页多选需要给表格table的:row-key设置唯一标识，在type="selection" 后添加:reserve-selection="true"，即保存跨页的多选状态
              适用场景：列表页导出、批量操作 不适用于有回显的（新增编辑详情同一组件情况需要特别处理）-->
          <el-table-column type="selection" :reserve-selection="true" width="55"/>
          <el-table-column property="articleId" label="文章ID" width="80"/>
          <el-table-column property="title" label="文章标题"  />
          <el-table-column property="userId" label="用户ID"  width="80"/>
          <el-table-column property="labelId" label="标签ID"  width="80"/>
          <el-table-column property="recommend" label="推荐指数"  width="100"/>
          <el-table-column property="pubDate" label="发行日期"  />
          <el-table-column fixed="right" label="文章操作" width="120">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="openPreviewDrawer(scope.row)">预览</el-button>
              <el-button link type="primary" size="small" @click="openEditDrawer(scope.row)">修改</el-button>
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
                      placeholder="请选择标签"
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
                      placeholder="请选择分类"
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
                <el-form-item prop="title" label="文章标题">
                  <el-input v-model="editFormDate.title"/>
                </el-form-item>
                <el-form-item prop="userId" label="用户ID">
                  <el-select
                      placeholder="请选择用户"
                      v-model="editFormDate.userId">
                    <el-option
                        v-for="item in userList"
                        :key="item.userId"
                        :value="item.userId"
                        :label="item.userName"/>
                  </el-select>
                </el-form-item>
                <el-form-item prop="labelId" label="标签ID">
                  <el-select
                      placeholder="请选择标签"
                      v-model="editFormDate.labelId">
                    <el-option
                        v-for="item in labelList"
                        :key="item.labelId"
                        :value="item.labelId"
                        :label="item.labelName"/>
                  </el-select>
                </el-form-item>
                <el-form-item prop="sortId" label="分类ID">
                  <el-select
                      placeholder="请选择分类"
                      v-model="editFormDate.sortId">
                    <el-option
                        v-for="item in sortList"
                        :key="item.sortId"
                        :value="item.sortId"
                        :label="item.sortName"/>
                  </el-select>
                </el-form-item>
                <el-form-item prop="recommend" label="推荐指数">
                  <el-rate v-model="editFormDate.recommend"
                           :colors="['#409eff', '#67c03a', '#f36c6c']"
                           :texts="['不推荐','差强人意','一般','很不错','十分推荐']"
                           show-text
                           clearable/>
                </el-form-item>
                <el-form-item prop="pubDate" label="发行日期">
                  <el-date-picker v-model="editFormDate.pubDate"
                                  type="date"
                                  label="Pick a date"
                                  placeholder="Pick a date"
                                  style="width: 100%"/>
                </el-form-item>
              </el-form>
              <div class="editArea" style="border: 1px solid #ccc">
                <Toolbar
                    style="border-bottom: 1px solid #ccc"
                    :editor="editorRef_edit"
                    :defaultConfig="toolbarConfig"
                    :mode=mode
                />
                <Editor
                    style="height: 676px; overflow-y: hidden;"
                    v-model="valueHtml_edit"
                    :defaultConfig="editorConfig"
                    :mode=mode
                    @onCreated="handleCreated_edit"
                />
              </div>
            </div>
            <div class="etDBtn">
              <el-button color="#67c03a" round @click="edit">修改文章</el-button>
            </div>
          </div>
        </el-scrollbar>
      </el-drawer>
      <el-drawer v-model="previewDrawer"
                 :with-header="false"
                 size="80%"
                 destroy-on-close
                 :direction="direction">
        <el-scrollbar>
          <div class="previewDrawer">
            <div class="previewArea">
              <Editor
                  style="height: 676px; overflow-y: hidden;"
                  v-model="valueHtml_preview"
                  :defaultConfig="editorConfig"
                  :mode=mode
                  @onCreated="handleCreated_preview"
              />
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
import {addArticle, updateArticle, deleteArticle} from "@/request/api/article";
let current = ref(1)
let size = ref(10)
let inputSearch = ref('')
let accTable = ref()
let addDrawer = ref(false)
let editDrawer = ref(false)
let previewDrawer = ref(false)
let direction = ref('ttb')
let addFormRef = ref()
let editFormRef = ref()
let addFormDate = reactive({
  userId:'',
  labelId:'',
  sortId:'',
  recommend:ref(),
  title:'',
  pubDate:'',
  content:'',
})
let editFormDate = reactive({
  userId:'',
  labelId:'',
  sortId:'',
  recommend:ref(),
  title:'',
  pubDate:'',
  content:'',
  articleId:'',
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
let articleIdList = []
//编辑器实例
const editorRef = shallowRef()
const editorRef_edit = shallowRef()
const editorRef_preview = shallowRef()
//内容Html
const valueHtml = ref('')
const valueHtml_edit = ref('')
const valueHtml_preview = ref('hahahah')
//编辑器配置
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
  const editor2 = editorRef_edit.value
  if (editor == null && editor2 == null) return
  editor.destroy()
  editor2.destroy()
})
const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
const handleCreated_edit = (editor) => {
  editorRef_edit.value = editor // 记录 editor 实例，重要！
}
const handleCreated_preview = (editor) => {
  editorRef_preview.value = editor // 记录 editor 实例，重要！
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
const deleteArticles = async () => {
  let res = await deleteArticle(articleIdList)
  //重新请求表格table数据
  await store.dispatch("articleModule/getArticleByPage",{current:current.value,size:size.value})
  console.log(res)
}
const openEditDrawer = (e) => {
  editFormDate = reactive({
    content:e.content,
    labelId:e.labelId,
    pubDate:e.pubDate,
    recommend:ref(e.recommend),
    sortId:e.sortId,
    title:e.title,
    userId:e.userId,
    articleId:e.articleId,
  })
  //延时执行
  const delayTime = setTimeout(()=>{
    // console.log(editorRef_edit.value)
    editorRef_edit.value.setHtml(e.content)
  },500)
  editDrawer.value = true
  userList = store.state.userModule.userList
  labelList = store.state.LabelModule.labelList
  sortList = store.state.sortModule.sortList
}
const openPreviewDrawer = (e) => {
  previewDrawer.value = true
  //延时执行
  const delayTime = setTimeout(()=>{
    editorRef_preview.value.setHtml(e.content)
    editorRef_preview.value.disable()
  },500)

}
const ArticleSelection = () => {
  let tempList = []
  for (let i = 0; i < accTable.value.getSelectionRows().length; i++){
    tempList.push(accTable.value.getSelectionRows()[i].articleId)
  }
  articleIdList = tempList
}
const submit = async () => {
  await addFormRef.value.validate(async (value) => {
    if (value){
      const editor = editorRef.value
      //格式化Html会报错，当文本内容空格多时
      // addFormDate.content = xmlFormat(editor.getHtml())
      addFormDate.content = editor.getHtml()
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
      //重置富文本编辑器
      editor.setHtml("")

      //重新请求表格table数据
      await store.dispatch("articleModule/getArticleByPage",{current:current.value,size:size.value})
    }else {
      console.log("表单不完整")
    }
  })
}
const edit = async () => {
  await editFormRef.value.validate(async (value) => {
    if (value){

      editFormDate.content = editorRef_edit.value.getHtml()
      let res = await updateArticle(editFormDate)
      console.log(res)
      editDrawer.value = false

      //重置富文本编辑器
      editorRef_edit.value.setHtml("")

      //重新请求表格table数据
      await store.dispatch("articleModule/getArticleByPage",{current:current.value,size:size.value})
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
  background-color: #eaeaef;
  border-radius: 1rem;
  box-shadow: 0 0 10px 2px rgba(227, 228, 240, 0.24);

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
  /*background-color: rgba(197, 148, 75, 0.91);*/
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
.addDrawer,
.editDrawer,
.previewDrawer{
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
.editArea {
  height: 800px;
  width: 90%;
}
.previewArea {
  width: 100%;
}
</style>
