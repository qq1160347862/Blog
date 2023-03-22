<template>
  <router-view v-if="isRouterAlive"></router-view>
  <live2-d></live2-d>
  <sideNav class="sideNav"></sideNav>
  <div id="rainBox"></div>
  <musicPlayer :class="{musicPlayer:store.state.musicModule.isMusicPlayerShow,
                        musicPlayer_close:!store.state.musicModule.isMusicPlayerShow}"></musicPlayer>
</template>

<script>
import $ from 'jquery'
/*下面时重新加载本页面的快速方法，在需要的地方inject['reload']，然后调用this.reload()就好*/
export default {
  name:'App',
  provide (){
    return {
      reload:this.reload
    }
  },
  data() {
    return {
      isRouterAlive:true
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(()=>{
        this.isRouterAlive = true
      })
    },
  },

}

</script>

<script setup>
import sideNav from "@/components/sideNav"
import musicPlayer from "@/components/musicPlayer"
import store from "@/store";
import {onMounted} from "vue";
import Live2D from '@/components/Live2D/index'
let clickAnimation = document.createElement("div")
document.body.appendChild(clickAnimation)
clickAnimation.id = "clickAnimation"
clickAnimation.className = "clickAnimation"
clickAnimation.innerHTML = ""
clickAnimation.target = ""
clickAnimation.ifDown = false

clickAnimation.onmousedown = (e) =>{

  if(!clickAnimation.ifDown){
    clickAnimation.ifDown = true
    clickAnimation.style.zIndex = 1000
    clickAnimation.style.left = e.clientX + "px"
    clickAnimation.style.top = e.clientY + "px"



    let temp = document.getElementsByClassName("clickAnimation")
    for(let i = 0; i < temp.length; i++){
      temp[i].className = temp[i].className.substring(0,temp[i].className.length-5)

    }

    // temp.classList.add("clickAnimation")

    // clickAnimation.target.onmousedown()
    document.onmouseup = () =>{
      if(clickAnimation.ifDown){
        clickAnimation.ifDown = false
        let temp = document.getElementsByClassName("clickAnim")
        for(let i = 0; i < temp.length; i++){
          temp[i].className = temp[i].className + "ation"

        }
        // temp.classList.remove("clickAnimation")
        // clickAnimation.target.onmouseup()
      }
      document.onmouseup = null
    }
  }
}


let click = (e,target) => {
  if(!clickAnimation.ifDown){
    clickAnimation.target = target
    clickAnimation.onmousedown(e)
  }

}
document.onmousedown = (e) =>{
  click(e,this);
}

//DOM元素挂载之后执行 下雨特效
onMounted(() => {
//   const rainBox = document.getElementById("rainBox")
//   const MaxCount = 30
//   let rainBoxWidth = rainBox.clientWidth;
//   let rainBoxHeight = rainBox.clientHeight;
// //页面改变时，重新赋值
//   window.onresize = function () {
//     rainBoxWidth = rainBox.clientWidth;
//     rainBoxHeight = rainBox.clientHeight;
//   }
// //每隔一段时间，生成雨滴
//   setInterval(() => {
//     let rain = document.createElement("div")
//     rain.classList.add("rain")
//     rain.style.top = 0
//
//     rain.style.left = Math.random() * rainBoxWidth + "px"
//     rain.style.height = Math.random() * 3 + "rem"
//     rain.style.opacity = Math.random()
//     rainBox.append(rain)
//
//     let race = 1
//     const timer = setInterval(() => {
//       if (parseInt(rain.style.top) > rainBoxHeight) {
//         clearInterval(timer)
//         rainBox.removeChild(rain)
//       }
//       race++
//       rain.style.top = parseInt(rain.style.top) + race + "px"
//     }, 20)
//   }, 250)
})
/*看板娘初始化
    参数说明
    model 模型的配置
       json 文件资源路径，可以支持网络路径，此处使用的是相对路径
       scale 模型缩放系数
    display 模型布局
       position 位置
       width 宽度
       height 高度
       hOffset 水平偏移量
       vOffset 垂直偏移量
    mobile 移动端配置
       show 显示/隐藏
       scale 缩放比例
    react 模型矩形框样式
       opacity 透明度
       opacityDefault 默认透明度
       opacityOnHover 鼠标悬浮透明度
  */
// window.L2Dwidget.init({
//   pluginRootPath: 'live2dw/',
//   pluginJsPath: 'lib/',
//   pluginModelPath: 'live2d-widget-model-haru_2/assets/', //中间这个haru_2就是你的老婆,想换个老婆,换这个就可以了
//   tagMode: false,
//   debug: false,
//   model: { jsonPath: 'live2dw/live2d-widget-model-haru_2/assets/haru02.model.json' },
//   display: { position: 'left', width: 350, height: 800 },  //调整大小,和位置
//   mobile: { show: true },   //要不要盯着你的鼠标看
//   log: false,
// })
</script>


<style>
#clickAnimation {
  pointer-events: none;
  position: fixed;
  left: 0;
  top: 0;
  z-index: -10;
  transform: translate(-50%,-50%);
  user-select: none;



}
.clickAnimation {


  border-radius: 50%;
  border: white solid 2px;
  box-shadow: 0 0 10px white;
  animation: circle-click-anime 0.2s forwards;

}
@keyframes circle-click-anime {
  0%{
    width: 0;
    height: 0;
  }
  100%{
    width: 100px;
    height: 100px;
  }
  100%{
    border: 0;
    box-shadow: 0 0 0 white;
  }
}

.sideNav {
  position: fixed;
  top: 40%;
  left: 0;
  transform: translate(-50%,-50%);
}
.musicPlayer {
  position: fixed;
  left: 50%;
  bottom: 0;
  transform: translateX(-50%);
  transition: 0.8s ease-in-out;
}
.musicPlayer_close {
  position: fixed;
  left: 50%;
  bottom: -10%;
  transform: translateX(-50%);
  transition: 0.8s ease-in-out;
}



#rainBox {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  /*不阻挡其他组件事件的触发*/
  pointer-events: none;
}
.rain {
  width: 0.2rem;
  background-color: rgba(255, 255, 255, 0.6);
  position: absolute;
}
</style>
