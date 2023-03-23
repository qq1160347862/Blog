import service from "../index.js";

//测试使用中
export const getUser = () => {
    return service({
        method:"GET",
        url:"/user"
    })
}

export const login = (data) => {
  return service({
      method:"POST",
      url:"/user/login",
      data:{
          userName:data.userName,
          password:data.password
      },
      header:{

      },
      auth:{

      }
  })
}

export const getUserIdAndUserName = () => {
  return service({
      method:"GET",
      url:"/user/getUserIdAndUserName"
  })
}

/************************ 管理层 *******************************/

export const UserByPage = (data) => {
    return service({
        method:"GET",
        url:"/user",
        params:{
            current:data.current,
            size:data.size
        }
    })

}

export const addUser = (data) => {
  return service({
      method:"POST",
      url:"/user",
      data:{
          userName:data.userName,
          password:data.password,
          userDesc:data.userDesc
      }
  })
}

export const updateUser = (data) => {
    return service({
        method:"PUT",
        url:"/user",
        data:{
            userName:data.userName,
            password:data.password,
            userDesc:data.userDesc,
            userId:data.userId
        }
    })
}

export const deleteUser = (data) => {
    return service({
        method:"DELETE",
        url:"/user",
        data:{
            idList: data
        }
    })
}