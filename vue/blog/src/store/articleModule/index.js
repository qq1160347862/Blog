import {
    ArticleByPage,
    getArticleByPage,
    getArticleByPageByHistory,
    getArticleByPageByRecommend,
    LikeArticleByPage
} from "@/request/api/article";

const articleModule = {
    namespaced:true,
    state:{
        articleList:[],
        articleIndex:0,
        total:0,

        //管理层
        article:[],
        articleTotal:0,
    },
    mutations:{
        updateArticleList(state,value){
            state.articleList = value
        },
        updateArticleIndex(state,value){
            state.articleIndex = value
        },
        updateTotal(state,value){
            state.total = value
        },
        updateArticle(state,value){
            state.article = value
        },
        updateArticleTotal(state,value){
            state.articleTotal = value
        },
    },
    actions:{
        //默认按时间排序
        async getArticleList(context,value){


            let res = await getArticleByPage(value)
            if (res.data.code === 2000){
                let tempList = []
                for (let i = 0; i < res.data.data.articleByPage.records.length; i++){
                    //向临时数组 从后插入
                    tempList.push(res.data.data.articleByPage.records[i])
                }
                context.commit('updateArticleList',tempList)
                context.commit('updateTotal',res.data.data.articleByPage.total)
            }else {
                console.log("数据获取失败")
            }
        },

        //按历史记录排序
        async getArticleListByHistory(context,value){


            let res = await getArticleByPageByHistory(value)
            if (res.data.code === 2000){
                let tempList = []
                for (let i = 0; i < res.data.data.articleByPage.records.length; i++){
                    //向临时数组 从后插入
                    tempList.push(res.data.data.articleByPage.records[i])
                }
                context.commit('updateArticleList',tempList)
                context.commit('updateTotal',res.data.data.articleByPage.total)
            }else {
                console.log("数据获取失败")
            }
        },

        //按推荐指数排序
        async getArticleListByRecommend(context,value){


            let res = await getArticleByPageByRecommend(value)
            if (res.data.code === 2000){
                let tempList = []
                for (let i = 0; i < res.data.data.articleByPage.records.length; i++){
                    //向临时数组 从后插入
                    tempList.push(res.data.data.articleByPage.records[i])
                }
                context.commit('updateArticleList',tempList)
                context.commit('updateTotal',res.data.data.articleByPage.total)
            }else {
                console.log("数据获取失败")
            }
        },

        //分页获取文章(无条件)
        async getArticleByPage(context,value){
            let res = await ArticleByPage(value)
            if (res.data.code === 2000){
                context.commit("updateArticle",res.data.data.articleByPage.records)
                context.commit("updateArticleTotal",res.data.data.articleByPage.total)
            }else {
                console.log("数据获取失败")
            }
        },
        //分页获取文章(模糊查询)
        async likeArticleByPage(context,value){
          let res = await LikeArticleByPage(value)
            if (res.data.code === 2000){
                context.commit("updateArticle",res.data.data.likeArticleByPage.records)
                context.commit("updateArticleTotal",res.data.data.likeArticleByPage.total)
            }else {
                console.log("数据获取失败")
            }
        }

    }

}

export default articleModule