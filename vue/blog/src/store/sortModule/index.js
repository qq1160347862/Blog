import {getSortIdAndSortName} from "@/request/api/sort";


const sortModule = {
    namespaced:true,
    state:{
        sortList:[]
    },
    mutations:{
        updateSortList(state,value){
            state.sortList = value
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
        }
    }
}
export default sortModule