import {getUserIdAndUserName, login} from "@/request/api/user";

const userModule = {
    namespaced:true,
    state: {
        token:'null',
        userList:[]
    },
    mutations: {
        updateToken(state,value){
            state.token = value
        },
        updateUserList(state,value){
            state.userList = value
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
        async UserIdAndUserName(context){
            let res = await getUserIdAndUserName()
            if (res.data.code === 2000){
                context.commit('updateUserList',res.data.data.list)
            }else {
                console.log("返回username和userid异常")
            }
        }
    }

}
export default userModule