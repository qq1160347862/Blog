<template>
  <div class="adminTopNavContainer">
    <div class="adminHeadImg">
      <img src="../../assets/headimg.jpg">
    </div>
    <div class="adminTitle">
      <span>后台管理</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "adminTopNav"
}
</script>

<style scoped>
.adminTopNavContainer {
  height: 3.5rem;
  width: 100%;
  background-color: #545c64;

  display: flex;
  align-items: center;
  flex-direction: row-reverse;
  justify-content: space-between;

}
.adminHeadImg {
  margin-right: 1rem;
}
.adminHeadImg img {
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 50%;
}
.adminTitle {
  font-weight: bold;
  font-size: 1.8rem;
  color: #FFFFFF;
  margin-left: 1rem;
}
</style>