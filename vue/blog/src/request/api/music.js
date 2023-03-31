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