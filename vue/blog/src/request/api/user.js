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