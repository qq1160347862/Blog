<template>
  <div class="sideNavContainer">
    <div class="sideNavButton" id="sideNavButton" >
      <div class="ButtonArea" >
        <ul>
          <li @click="openMusicPlayerNav">
            <div>
              <div class="sideNavButtonImage">
                <el-icon>
                  <Service/>
                </el-icon>
              </div>
              <div class="sideNavButtonImageTitle">音乐</div>
            </div>
          </li>
          <li @click="test">
            <div>
              <div class="sideNavButtonImage">
                <el-icon>
                  <Service/>
                </el-icon>
              </div>
              <div class="sideNavButtonImageTitle">音乐</div>
            </div>
          </li>
          <li>
            <div>
              <div class="sideNavButtonImage">
                <el-icon>
                  <Service/>
                </el-icon>
              </div>
              <div class="sideNavButtonImageTitle">音乐</div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="on_off" @click="openSideNav">
      <el-icon>
        <CloseBold/>
      </el-icon>
    </div>
  </div>

</template>

<script>
import {Service, CloseBold} from "@element-plus/icons-vue";
import $ from 'jquery'
export default {
  name: "sideNav",
  methods: {}
}
</script>
<script setup>
import store from "@/store";
import {getUser} from '@/request/api/user'
let isOpen = false
let isMusicPlayerNavOpen = false

const openSideNav = () =>{

  let sideNav = $("#sideNavButton")
  let on_off = $(".on_off")
  if (isOpen == false){
    isOpen = true
    sideNav.addClass("active")
    sideNav.removeClass("hide")

    on_off.addClass("active")
    on_off.removeClass("hide")
    // sideNav.toggleClass("active")
  }else {
    isOpen = false
    sideNav.addClass("hide")
    sideNav.removeClass("active")

    on_off.addClass("hide")
    on_off.removeClass("active")
  }
}
const openMusicPlayerNav = () => {
  if(!isMusicPlayerNavOpen){
    isMusicPlayerNavOpen = true

    store.commit("musicModule/updateIsMusicPlayerShow",isMusicPlayerNavOpen,{root : true})
  }else {
    isMusicPlayerNavOpen = false
    store.commit("musicModule/updateIsMusicPlayerShow",isMusicPlayerNavOpen,{root:true})
  }

}
const  test = () => {
  getUser().then((res) => {
    console.log(res)
  })
}


</script>

<style scoped>
.on_off{
  width: 4rem;
  height: 4rem;
  background-color: rgba(253, 199, 199, 0.5);
  border-radius: 50%;
  position: absolute;
  top: 0%;
  left: 0;
  transition: left 0.5s,top 0.5s 0.5s,
  border-radius 0.2s 0.5s,
  width 0.5s 0.5s,height 0.5s 0.5s,
  font-size 0.5s 1s;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  font-size: 0;
  color: #FFFFFF;
}
.active.on_off {
  top: 100%;
  left: 50%;
  width: 4rem;
  height: 4rem;
  border-radius: 0 0 4rem 4rem;
  font-size: 1.8rem;
}
.hide.on_off {
  top: 0%;
  left: 0;
  width: 4rem;
  height: 4rem;
  transition: left 0.5s 0.7s,top 0.5s 0.2s,
  border-radius 0.2s 0.7s,
  width 0.5s 0.2s,height 0.5s 0.2s;
  font-size: 0;
}



.sideNavButton{
  position: relative;
  border-radius: 50%;
  width: 4rem;
  height: 4rem;
  cursor: pointer;
  background-color: rgba(253, 199, 199, 0.5);
  left: 0;
}
.active.sideNavButton{

  left: 50%;
  height: 24rem;
  width: 4rem;
  border-radius: 4rem 4rem 0 0;


  transition: left 0.5s,
  border-radius 0.2s 0.5s,
  width 0.5s 0.5s,
  height 0.5s 0.5s;

  cursor: auto;
}
.hide.sideNavButton {
  left: 0;
  transition:
  width 0.5s 0.2s,
  height 0.5s 0.2s,
  border-radius 0.1s 0.7s,
  left 0.5s 0.7s;
}


.ButtonArea {
  width: 100%;
  height: 100%;
  border-radius: 50%;

}
.active.sideNavButton ul{
  opacity: 1;
  transition: left 0.1s,opacity 0.5s 1s;
  width: 100%;
  height: 100%;
  left: 0;
}
.hide.sideNavButton ul{
  opacity: 0;
  transition: opacity 0.4s,left 0.1s 0.4s;
  left: -100%;
  width: 100%;
  height: 100%;
}
.ButtonArea ul{
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  opacity: 0;
  position: absolute;
  left: -100%;
}


.ButtonArea ul li{
  flex: 1;
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.ButtonArea ul li>div{
  background-color: #f8ba77;
  width: 3rem;
  height: 5rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  color: var(--sideNav-font-light-color);
  border-radius: .8rem;
  box-shadow: 0 0 10px white;
}
.ButtonArea ul li .sideNavButtonImage{
  width: 3rem;
  height: 3rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  font-size: 3rem;

}
.ButtonArea ul li .sideNavButtonImageTitle{
  width: 3rem;
  line-height: 2rem;
  text-align: center;
  height: 2rem;
  font-size: 1.2rem;
}
</style>