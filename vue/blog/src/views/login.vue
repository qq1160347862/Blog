<template>
  <div class="lgContainer">
    <div class="bg"></div>
    <div class="lgCard">
      <div class="lgImg">
        <div></div>
      </div>
      <div class="lgForm">
        <div>
          <div class="lgTitle"><span>登陆</span></div>
          <div class="lgInput">
            <el-form :model="formDate"
                     size="large"
                     :rules="rules"
                     status-icon ref="formRef">
              <el-form-item prop="username">
                <input type="text"
                       name="username"
                       class="username"
                       placeholder="清输入用户名" v-model="formDate.username">
              </el-form-item>
              <el-form-item prop="password">
                <input type="password"
                       name="password"
                       class="password"
                       placeholder="请输入密码" v-model="formDate.password">
              </el-form-item>
            </el-form>
          </div>
          <div class="lgBtn">
<!--            <button class="btn" @click="Login">登录</button>-->
<!--            <button @click="$router.push('/home')">立即体验</button>-->
            <button class="login" @click="Login">登录</button>
            <button @click="backToHome">返回</button>
          </div>
          <div class="lgTip" id="lgTip" v-loading="loadingTip"
               element-loading-text="登陆中..."
               element-loading-background="rgba(122, 122, 122, 0)">仅管理员进入后台</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {ElLoading} from 'element-plus'
export default {
  name: "login",
  methods:{

  },
}
</script>
<script setup>
import {ref, reactive} from 'vue'
import store from "@/store";
import {useRouter} from "vue-router";
const router = useRouter()
let loadingTip = ref(false)
let formRef = ref()
let formDate = reactive({
  username:'',
  password:''
})
let username = ""
let password = ""
let rules = reactive({
  username:[
    { required: true, message: '请输入账号', trigger:'blur'},
  ],
  password:[
    { required: true, message: '请输入密码', trigger:'blur'},
  ]
})
const Login = async () => {
  await formRef.value.validate(async (value) => {
    if (value){
      console.log("合法")
      loadingTip.value = true
      let res = await store.dispatch("userModule/getLogin",{userName:formDate.username,password:formDate.password})
      loadingTip.value = false
      console.log(res)
      await router.push('/admin')
    }else {
      console.log("不合法")
    }
  })
}
const backToHome = () => {
  router.push("/home")
}
</script>

<style scoped>
.lgContainer {
  width: 100vw;
  height: 100vh;


  display: flex;
  justify-content: center;
  align-items: center;

}
.bg {
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  left: 0;
  z-index: -1;
  background-size: cover;
  background-repeat: no-repeat;
  background-image: url("@/assets/bg.jpg");
  filter: blur(0.5px);
}
.lgCard {
  width: 55rem;
  height: 32rem;
  background-color: rgba(0, 0, 0, 0.3);
  border-radius: 1rem;
  display: flex;
  align-items: center;
  backdrop-filter: blur(2px);
}
.lgImg {
  flex: 1;
  height: 100%;
  position: relative;
}
.lgImg>div {
  position: absolute;
  top: -10%;
  left: 10%;
  height: 120%;
  width: 110%;
  z-index: 2;

  background-image: url("@/assets/login.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  border-radius: 2vw;
  filter: blur(1px);

}
.lgForm {
  flex: 3;
  height: 100%;
  position: relative;
}
.lgForm>div {
  position: absolute;
  top: 0;
  right: 0;
  width: 100%;
  height: 100%;
  z-index: 1;

  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
}
.lgTitle {
  font-size: 2rem;
  color: #FFFFFF;
  font-weight: bold;
  text-indent: 8px;
}
.lgForm input {
  width: 24rem;
  height: 3.2rem;
  text-indent: 2rem;
  border: none;
  outline: none;
  border-bottom: #ddd solid 1px;
  border-radius: 4rem;
  margin: .2rem 0;
  font-size: 1rem;
}
.lgForm button {
  width: 8rem;
  height: 3rem;
  border:none;
  border-radius: 4rem;
  background-color: #f56c6c;
  color: #f9f9f9;
  font-weight: bold;
  font-size: 1.2rem;
}
.lgBtn {
  width: 60%;
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: space-evenly;
}
.lgTip {
  font-size: 1.2rem;
  color: #FFFFFF;
}

</style>