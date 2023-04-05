import {getMusicLyric} from "@/request/api/music";

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
                name:"无名氏",
                picUrl:"http://p4.music.126.net/GcRunGm02vZBicYmIN6GXw==/109951163200249252.jpg"
            },
            ar:[{
                name:"无名氏"
            }]

        }],
        musicIndex:0,
        playMode:0, //播放模式

        lyricList:{},//歌词
        currentTime:0,//当前播放时间
        duration:0,//歌曲总时长
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
        updatePlayMode(state,value){
          state.playMode = value
        },
        updateLyricList(state,value){
            state.lyricList = value
        },
        updateCurrentTime(state,value){
            state.currentTime = value
        },
        updateDuration(state,value){
            state.duration = value
        },



        //function
        addMusicToMusicListHead(state,value){
            let needAdd = 0
            //查找是否在歌曲列表中
            for(let i = 0;i<state.musicList.length;i++){
                if(state.musicList[i].id===value.id) {
                    console.log("歌曲存在，不必重复添加")
                    state.musicIndex = i
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
        deleteMusicFromListById(state,value){
            //查找即将删除的音乐
            for(let i = 0;i<state.musicList.length;i++){
                if(state.musicList[i].id===value) {
                    if (state.musicList.length > 1){
                        state.musicList.splice(i,1)
                        break
                    }else {
                        alert("歌库中至少剩余一首歌曲")
                    }

                }
            }
        },

    },
    actions:{
        //异步获得歌词数据
        getLyric:async function (context,value) {
            let res = await getMusicLyric(value)
            // console.log(res)
            context.commit("updateLyricList",res.data.lrc)
        },
    }
}

export default musicModule