import {getLabelIdAndLabelName, LabelByPage, LikeLabelByPage} from "@/request/api/label";

const labelModule =  {
    namespaced:true,
    state: {
      labelList:[], //下拉框数据

        //标签管理数据
        labels:[],
        total:0,
    },
    mutations:{
        updateLabelList(state,value){
            state.labelList = value
        },
        updateLabels(state,value){
            state.labels = value
        },
        updateTotal(state,value){
            state.total = value
        }
    },
    actions:{
        async LabelIdAndLabelName(context){
            let res =  await getLabelIdAndLabelName();
            if (res.data.code === 2000){
                context.commit('updateLabelList',res.data.data.list)
            }else {
                console.log("返回labelName和labelId异常")
            }
        },
        async getLabelByPage(context,value){
            let res = await LabelByPage(value)
            if (res.data.code === 2000){
                context.commit("updateLabels",res.data.data.labelByPage.records)
                context.commit("updateTotal",res.data.data.labelByPage.total)
            }else {
                console.log("数据获取失败")
            }
        },
        async likeLabelByPage(context,value){
            let res = await LikeLabelByPage(value)
            if (res.data.code === 2000){
                context.commit("updateLabels",res.data.data.likeLabelByPage.records)
                context.commit("updateTotal",res.data.data.likeLabelByPage.total)
            }else {
                console.log("数据获取失败")
            }
        }
    },
}
export default labelModule