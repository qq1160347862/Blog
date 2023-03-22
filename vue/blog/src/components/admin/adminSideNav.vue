<template>
  <div class="adminSideNavContainer">
    <el-menu
        active-text-color="#ffd04b"
        background-color="#545c64"
        mode="vertical"
        default-active="userController"
        text-color="#fff"
        unique-opened="unique-opened"
        :router="true"
    >
      <el-menu-item index="userController">
        <el-icon><icon-menu /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-menu-item index="articleController">
        <el-icon><icon-menu /></el-icon>
        <span>文章管理</span>
      </el-menu-item>
      <el-menu-item index="3">
        <el-icon><document /></el-icon>
        <span>分类管理</span>
      </el-menu-item>
      <el-menu-item index="4">
        <el-icon><setting /></el-icon>
        <span>标签管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "adminSideNav"
}
</script>
<script setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
</script>
<style scoped>
.adminSideNavContainer {
  height: calc(100vh - 3.5rem);
  width: 10rem;
}
.adminSideNavContainer>>>.el-menu {
  border-right: black solid 0;
}
.adminSideNavContainer ul {
  height: 100%;
  width: 10rem;
}
</style>