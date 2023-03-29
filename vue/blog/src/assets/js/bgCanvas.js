let ctx
let width
let height
let points = []
let timer = setInterval
let pointTimer = setInterval
let mouse = {
    x:-500,
    y:-500
}
// window.onload = function () {
//     document.querySelector('.homeScrollbar').addEventListener("mousemove",(e) => {
//         mouse.x = e.x
//         mouse.y = e.y
//         console.log("1")
//     })
// }
document.addEventListener("mousemove",(e) => {
    mouse.x = e.x
    mouse.y = e.y
})

class point {
    constructor() {
        this.x = 0
        this.y = 0
        this.vx = 0
        this.vy = 0
        this.age = 0
        this.opacity = 1
        this.isDead = true
        this.init()
    }
    init(){
        this.x = Math.random() * width
        this.y = Math.random() * height
        this.vy = (Math.random() - 0.5) * 2
        this.vx = (Math.random() - 0.5) * 2
        this.opacity = 1
        this.age = Math.random() * 50
        this.isDead = false
    }
    update(){
        this.x += this.vx
        this.y += this.vy
        // if (this.x > width || this.x < 0){
        //     this.vx = -this.vx
        // }
        // if (this.y > height || this.y < 0){
        //     this.vy = -this.vy
        // }
        if (this.x > width || this.x < 0 || this.y > height || this.y < 0){
            this.init()
        }
        if (this.age < 0){
            this.isDead = true
        }
        this.age--
        //鼠标扰动
        //点与鼠标的距离
        let dy = this.y - mouse.y
        let dx = this.x - mouse.x
        let r = Math.sqrt(Math.pow(dy,2) + Math.pow(dx,2))
        if (r < 200){
            this.x += dx / 50
            this.y += dy / 50
        }
    }
    draw(){
        ctx.beginPath()
        ctx.fillStyle = `rgba(0,0,0,${this.opacity})`
        ctx.arc(this.x, this.y, 1,0, Math.PI * 2)
        ctx.fill()
    }
}

const start = () => {
    if (points.length > 0){

    }else {
        for (let i = 0; i < 200; i++) {
            points.push(new point())
        }
    }

    // pointTimer = setInterval(()=>{
    //     if (points.length > 150){
    //         for (let i = 0; i < points.length; i++) {
    //             if (points[i].isDead){
    //                 points.pop()   //  删除该位置元素
    //                 console.log("dead")
    //             }
    //         }
    //     }
    //     if (points.length <= 150){
    //         for (let i = 0; i < 50; i++) {
    //             points.push(new point())
    //             console.log("生成")
    //         }
    //     }
    // },5000)

    //动态绘画
    timer = setInterval(() => {
        ctx.clearRect(0, 0, width, height)
        for (let i in points) {
            let point = points[i]
            point.update()
            point.draw()
            //线条绘制
            for (let j in points.slice(i,points.length)){
                let point2 = points[j]
                let r = Math.sqrt(Math.pow((point.x - point2.x),2) + Math.pow((point.y - point2.y),2))
                if (r < 150){
                    ctx.beginPath()
                    ctx.moveTo(point.x,point.y)
                    ctx.lineTo(point2.x,point2.y)
                    ctx.strokeStyle = `rgb(0, 0, 0, ${1 - r/150})`
                    ctx.stroke()
                }

            }

        }
        // console.log(points[0])
    },1000/60 )

}

export const free = () => {
    clearInterval(timer)
    clearInterval(pointTimer)
}

export const initBgCanvas = (ctx1,width1,height1) => {
    ctx = ctx1
    width = width1
    height = height1
    start()
}