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

//获取文章目录
export const getArticleCatalogue = () => {
  return service({
      method:"GET",
      url:"article/getArticleCatalogue"
  })
}
//根据文章ID查询文章
export const getArticleById = (data) => {
  return service({
      method:"GET",
      url:"/article/getArticleById",
      params:{
          articleId:data
      }
  })
}
//获取分类目录
export const getArticleAndSort = (data) => {
  return service({
      method:"GET",
      url:"/article/getArticleAndSort",
      params:{
          sortId:data
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