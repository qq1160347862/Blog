import service from "../index.js";

//默认按时间排序
export const getArticleByPage = (data) => {
    return service({
        method:"GET",
        url:"/article/findByPage",
        params:{
            current:data.current,
            size:data.size
        }
    })
}

//按历史记录排序
export const getArticleByPageByHistory = (data) => {
    return service({
        method:"GET",
        url:"/article/findByPageByHistory",
        params:{
            current:data.current,
            size:data.size
        }
    })
}

//按推荐指数排序
export const getArticleByPageByRecommend = (data) => {
    return service({
        method:"GET",
        url:"/article/findByPageByRecommend",
        params:{
            current:data.current,
            size:data.size
        }
    })
}