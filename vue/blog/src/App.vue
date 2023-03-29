<template>
  <router-view v-if="isRouterAlive"></router-view>
  <live2-d></live2-d>
  <sideNav class="sideNav"></sideNav>
<!--  <canvas id="rainCanvas"></canvas>-->
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
import {onBeforeUnmount, onMounted, reactive} from "vue";
import Live2D from '@/components/Live2D/index'
import {freeRain, initRainCanvas} from './assets/js/rainCanvas'

document.onmousedown = (e) =>{
  let clickAnimation = document.createElement("div")
  document.body.appendChild(clickAnimation)
  clickAnimation.className = "clickAnimation"
  clickAnimation.innerHTML = ""
  clickAnimation.target = ""
  clickAnimation.ifDown = false

  let click = (e,target) => {
    if(!clickAnimation.ifDown){
      clickAnimation.target = target
      clickAnimation.onmousedown(e)
    }
  }
  clickAnimation.onmousedown = (e) =>{

    if(!clickAnimation.ifDown){
      clickAnimation.ifDown = true
      clickAnimation.style.zIndex = 1000
      clickAnimation.style.left = e.clientX + "px"
      clickAnimation.style.top = e.clientY + "px"

      document.onmouseup = () =>{
        if(clickAnimation.ifDown){
          clickAnimation.ifDown = false
        }
        document.onmouseup = null
      }
    }
    setTimeout(()=>{
      document.body.removeChild(document.getElementsByClassName("clickAnimation")[0])
    },1000)
  }

  click(e,this)
}
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


//雨滴特效
// const rainData = reactive({
//   ctx:{},
//   initCanvas(){
//     let rainCanvas = document.getElementById("rainCanvas")
//     rainCanvas.width = window.innerWidth
//     rainCanvas.height = window.innerHeight
//     this.ctx = rainCanvas.getContext("2d")
//     initRainCanvas(this.ctx)
//   }
// })
// onMounted(()=> {
//   rainData.initCanvas()
// })
// onBeforeUnmount(()=>{
//   freeRain() //释放canvas
// })

</script>


<style>
.clickAnimation {

  pointer-events: none;
  position: fixed;
  left: 0;
  top: 0;
  z-index: -10;
  /*transform: translate(-50%,-50%);*/
  user-select: none;

  background-image: url("assets/floatLogo.png");
  background-size: cover;
  background-repeat: no-repeat;
  width:24px;
  height: 24px;
  border-radius: 50%;
  animation: circle-click-anime 1s forwards;

}
@keyframes circle-click-anime {
  0%{
    opacity: 1;
    transform: translate(-50%,-50%);
  }
  100%{
    opacity: 0;
    transform: translate(-50%,-50px);
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


#rainCanvas {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  /*不阻挡其他组件事件的触发*/
  pointer-events: none;
}
</style>
