import {getLabelIdAndLabelName} from "@/request/api/label";

const labelModule =  {
    namespaced:true,
    state: {
      labelList:[]
    },
    mutations:{
        updateLabelList(state,value){
            state.labelList = value
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
        }
    },
}
export default labelModule