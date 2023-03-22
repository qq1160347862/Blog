import service from "../index.js";

//测试使用中
export const getSortIdAndSortName = () => {
    return service({
        method:"GET",
        url:"/sort/getSortIdAndSortName"
    })
}