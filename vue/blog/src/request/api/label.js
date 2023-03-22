import service from "../index.js";

//测试使用中
export const getLabelIdAndLabelName = () => {
    return service({
        method:"GET",
        url:"/label/getLabelIdAndLabelName"
    })
}