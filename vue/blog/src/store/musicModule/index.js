
const musicModule = {
    namespaced:true, //命名空间 调用时路径为: moduleName/functionName
    state: {
        isMusicPlayerShow:false,
        isMusicPlaying:false,
        isMusicDrawerShow:false,

        musicList:[{
            id:27646205,
            name:"安和桥",
            al:{
                picUrl:"http://p4.music.126.net/GcRunGm02vZBicYmIN6GXw==/109951163200249252.jpg"
            }
        }],
        musicIndex:0,
        playMode:0 //播放模式
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
        },
        updateMode(state,value){
          state.playMode = value
        },



        //function
        addMusicToMusicListHead(state,value){
            let needAdd = 0
            //查找是否在歌曲列表中
            for(let i = 0;i<state.musicList.length;i++){
                if(state.musicList[i].id===value.id) {
                    console.log("歌曲存在，不必重复添加")
                    state.playListIndex = i
                    break
                }else {
                    needAdd = needAdd + 1
                }
            }
            if(needAdd===state.musicList.length){
                console.log("歌曲不存在，添加到歌单")
                //头部添加
                state.musicList.unshift(value)
                //添加歌曲后，当前播放下标转至最新歌曲
                state.musicIndex = 0
            }
        },
    },
}

export default musicModule