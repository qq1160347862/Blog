const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,  //vue组件名 多字组件名检测关闭


  // publicPath:process.env.NODE_ENV==='production'?'./':'/',
  publicPath:'/',

})
