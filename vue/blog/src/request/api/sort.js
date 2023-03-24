import service from "../index.js";



/************************ 管理层 *******************************/

export const getSortIdAndSortName = () => {
    return service({
        method:"GET",
        url:"/sort/getSortIdAndSortName"
    })
}

export const SortByPage = (data) => {
    return service({
        method:"GET",
        url:"/sort",
        params:{
            current:data.current,
            size:data.size
        }
    })

}

export const LikeSortByPage = (data) => {
    return service({
        method:"GET",
        url:"sort/likeSortByPage",
        params:{
            current:data.current,
            size:data.size,
            query:data.query
        }
    })
}

export const addSort = (data) => {
    return service({
        method:"POST",
        url:"/sort",
        data:{
            sortName:data.sortName,
        }
    })
}

export const updateSort = (data) => {
    return service({
        method:"PUT",
        url:"/sort",
        data:{
            sortName:data.sortName,
            sortId:data.sortId,
        }
    })
}

export const deleteSort = (data) => {
    return service({
        method:"DELETE",
        url:"/sort",
        data:{
            idList: data
        }
    })
}