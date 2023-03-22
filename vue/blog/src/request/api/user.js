import service from "../index.js";

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