import {service_music} from '../index'
//获取默认搜索关键词
export const getSearchWordDefault = () => {
    return service_music({
        method:"GET",
        url:'/search/default'
    })
}


//获取指定搜索建议词
export const getSuggestWord = (data) => {
    return service_music({
        method:"GET",
        url:`/search/suggest?keywords=${data}&type=mobile`
    })
}



//获取搜索音乐数据
export const getMusicSearched = (keywords,limit,offset) => {
    return service_music({
        method:"GET",
        url:'/cloudsearch',
        params:{
            keywords,
            limit,
            offset
        }
    })
}

//获取热搜列表(详细)
export const getSearchHotDetail = () => {
    return service_music({
        method:"GET",
        url:'/search/hot/detail'
    })
}

//获取歌词数据
export const getMusicLyric = (data) => {
    return service_music({
        method:"GET",
        url:`/lyric?id=${data}`
    })
}


//获取发现好歌单
export const getMusicList = () => {
    return service_music({
        method:"get",
        url:"/personalized?limit=8"
    })
}

//获取歌单所有歌曲
export const getMusicListSongAll = (id) => {
    return service_music({
        method:"GET",
        url:`/playlist/track/all?id=${id}`
    })
}


// 二维码登陆测试
export const getQrKey = () =>{
    return service_music({
        method:"GET",
        url:`/login/qr/key?timestamp=${Date.now()}`
    })
}

export const getQr = (key) =>{
    return service_music({
        method:"GET",
        url:`/login/qr/create?key=${key}&qrimg=true&timestamp=${Date.now()}`
    })
}
export const checkQr = (key) =>{
    return service_music({
        method:"GET",
        url:`/login/qr/check?key=${key}&timestamp=${Date.now()}`
    })
}

export const getLoginStatus = (cookie = '') => {
  return service_music({
      method:"POST",
      url:`/login/status?timestamp=${Date.now()}`,
      data:{
          cookie,
      }
  })
}