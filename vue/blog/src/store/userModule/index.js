import {login} from "@/request/api/user";

const userModule = {
    namespaced:true,
    state: {
        token:'null'
    },
    mutations: {
        updateToken(state,value){
            state.token = value
        }
    },
    //dispatch
    actions:{
        //异步登陆
        async getLogin(context,value){
            let res = await login(value)
            //如果用户存在，返回Token
            if (res.data.code === 2000){
                context.commit('updateToken',res.data.data.token)
                localStorage.setItem("token",res.data.data.token)
            }else {
                console.log("登陆失败")
            }
            return res.data.message
        },
    }

}
export default userModule