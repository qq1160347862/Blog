import {getSortIdAndSortName, SortByPage} from "@/request/api/sort";
import {LabelByPage} from "@/request/api/label";


const sortModule = {
    namespaced:true,
    state:{
        sortList:[],//下拉框数据

        //分类管理数据
        sorts:[],
        total:0,
    },
    mutations:{
        updateSortList(state,value){
            state.sortList = value
        },
        updateSorts(state,value){
            state.sorts = value
        },
        updateTotal(state,value){
            state.total = value
        }
    },
    actions:{
        async SortIdAndSortName(context){
            let res =  await getSortIdAndSortName();
            if (res.data.code === 2000){
                context.commit('updateSortList',res.data.data.list)
            }else {
                console.log("返回sortName和sortId异常")
            }
        },
        async getSortByPage(context,value){
            let res = await SortByPage(value)
            if (res.data.code === 2000){
                context.commit("updateSorts",res.data.data.sortByPage.records)
                context.commit("updateTotal",res.data.data.sortByPage.total)
            }else {
                console.log("数据获取失败")
            }
        }
    }
}
export default sortModule