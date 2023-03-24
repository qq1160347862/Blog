import service from "../index.js";



/************************ 管理层 *******************************/

export const getLabelIdAndLabelName = () => {
    return service({
        method:"GET",
        url:"/label/getLabelIdAndLabelName"
    })
}

export const LabelByPage = (data) => {
    return service({
        method:"GET",
        url:"/label",
        params:{
            current:data.current,
            size:data.size
        }
    })

}
export const LikeLabelByPage = (data) => {
    return service({
        method:"GET",
        url:"label/likeLabelByPage",
        params:{
            current:data.current,
            size:data.size,
            query:data.query
        }
    })
}

export const addLabel = (data) => {
    return service({
        method:"POST",
        url:"/label",
        data:{
            labelName:data.labelName,
        }
    })
}

export const updateLabel = (data) => {
    return service({
        method:"PUT",
        url:"/label",
        data:{
            labelName:data.labelName,
            labelId:data.labelId,
        }
    })
}

export const deleteLabel = (data) => {
    return service({
        method:"DELETE",
        url:"/label",
        data:{
            idList: data
        }
    })
}