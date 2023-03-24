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


/************************ 管理层 *******************************/
//获取文章数据
export const ArticleByPage = (data) => {
  return service({
      method:"GET",
      url:"/article/ArticleByPage",
      params:{
          current:data.current,
          size:data.size
      }
  })
}
export const LikeArticleByPage = (data) => {
  return service({
      method:"GET",
      url:"article/likeArticleByPage",
      params:{
          current:data.current,
          size:data.size,
          query:data.query
      }
  })
}


export const addArticle = (data) => {
  return service({
      method:"POST",
      url:"/article",
      data:{
          title:data.title,
          content:data.content,
          userId:data.userId,
          sortId:data.sortId,
          historyCount:0,
          labelId:data.labelId,
          pubDate:data.pubDate,
          recommend:data.recommend
      }
  })
}

export const updateArticle = (data) => {
  return service({
      method:"PUT",
      url:"/article",
      data:{
          title:data.title,
          content:data.content,
          userId:data.userId,
          sortId:data.sortId,
          historyCount:0,
          labelId:data.labelId,
          pubDate:data.pubDate,
          recommend:data.recommend,
          articleId:data.articleId
      }
  })
}

export const deleteArticle = (data) => {
  return service({
      method:"DELETE",
      url:"/article",
      data:{
          idList:data
      }
  })
}