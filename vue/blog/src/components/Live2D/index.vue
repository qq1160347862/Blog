<template>
  <div class="index">
    <div class="waifu">

      <!-- 提示框 -->
      <div class="waifu-tips"></div>

      <!-- 看板娘画布 -->
      <canvas id="live2d" class="live2d"/>

      <!-- 工具栏 -->
      <div class="waifu-tool">
        <p class="fui-home">
          <el-icon><HomeFilled /></el-icon>
        </p>
        <p class="fui-chat">
          <el-icon><Comment /></el-icon>
        </p>
        <p class="fui-eye">
          <el-icon><Avatar /></el-icon>
        </p>
        <p class="fui-user">
          <el-icon><Switch /></el-icon>
        </p>
        <p class="fui-photo">
          <el-icon><CameraFilled /></el-icon>
        </p>
        <p class="fui-info-circle">
          <i class="el-icon-s-comment"/>
        </p>
        <p class="fui-cross">
          <el-icon><CircleCloseFilled /></el-icon>
        </p>
      </div>

    </div>
  </div>
</template>

<script>
  import '@/assets/live2d/waifu-tips'
  import '@/assets/live2d/live2d'
  import '@/assets/live2d/waifu.css'
  import { initModel } from '@/assets/live2d/waifu-tips'
  import {HomeFilled,Comment,Avatar,Switch,CameraFilled,CircleCloseFilled} from '@element-plus/icons-vue'
  export default {
    name: 'PhyLive2d',
    components: {
      HomeFilled,
      Comment,
      Avatar,
      Switch,
      CameraFilled,
      CircleCloseFilled
    },
    data() {
      return {
      }
    },
    mounted () {
      live2d_settings['modelId'] = 5;                  // 默认模型 ID
      live2d_settings['modelTexturesId'] = 1;          // 默认材质 ID
      /* 在 initModel 前添加 */
      initModel(require("@/assets/live2d/waifu-tips.json"))
    },
    methods: {
    }
  }
</script>

<style  scoped>
  .waifu-tool p i {
    cursor: pointer;
  }
</style>
