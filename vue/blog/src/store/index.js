import {createStore} from 'vuex'
import createPersistedState from "vuex-persistedstate"
import userModule from '@/store/userModule/index'
import articleModule from '@/store/articleModule/index'
import LabelModule from "@/store/labelModule";
import sortModule from "@/store/sortModule";
import musicModule from "@/store/musicModule"


export default createStore({
    modules :{
        musicModule: musicModule,
        userModule: userModule,
        articleModule: articleModule,
        LabelModule:LabelModule,
        sortModule:sortModule
    },
    //持久化vuex
    plugins: [createPersistedState({
        storage:window.sessionStorage  // 同localStorage相同，只是将vuex的所有值存储到sessionStorage中
    })]
})