
const musicModule = {
    namespaced:true, //命名空间 调用时路径为: moduleName/functionName
    state: {
        isMusicPlayerShow:false,
        isMusicPlaying:false,
        isMusicDrawerShow:false
    },
    //同步操作 commit("函数名",参数)
    mutations: {
        updateIsMusicPlayerShow(state,value){
            state.isMusicPlayerShow = value
        },
        updateIsMusicPlaying(state,value){
            state.isMusicPlaying = value
        },
        updateIsMusicDrawerShow(state,value){
            state.isMusicDrawerShow  =value
        }
    },
}

export default musicModule