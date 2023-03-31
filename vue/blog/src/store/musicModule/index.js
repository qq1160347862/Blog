
const musicModule = {
    namespaced:true, //命名空间 调用时路径为: moduleName/functionName
    state: {
        isMusicPlayerShow:false,
        isMusicPlaying:false,
        isMusicDrawerShow:false,

        musicList:[{
            id:27646205,
        }],
        musicIndex:0,
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
        },
        updateMusicList(state,value){
            state.musicList = value
        },
        updateMusicIndex(state,value){
            state.musicIndex = value
        }
    },
}

export default musicModule