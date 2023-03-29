let ctx
let width = window.innerWidth
let height = window.innerHeight
let timer = setInterval
let rains = []
class rain {
    constructor() {
        this.x = 0
        this.y = 0
        this.vy = 0
        this.opacity = 1
        this.init()
    }
    init(){
        this.x = Math.random() * width
        this.y = 0
        this.vy = 5 + Math.random() * 10
        this.opacity = Math.random()
    }
    update(){
        this.y += this.vy
        if (this.y > height){
            this.init()
        }
    }
    draw(){
        // ctx.beginPath()
        // ctx.strokeStyle = `rgba(221,221,221,${this.opacity})`;
        // ctx.moveTo(this.x, this.y);
        // ctx.lineTo(this.x, this.y + 15);
        ctx.fillStyle = `rgba(221,221,221,${this.opacity})`
        ctx.fillRect(this.x, this.y, 2, 15)
        // ctx.stroke();

    }
}


const start = () => {
    console.log("初始化雨滴")
    //生成雨滴对象
    for (let i = 0; i < 50; i++) {
        rains.push(new rain())
    }

    //动态绘画
    timer = setInterval(() => {
        ctx.clearRect(0, 0, width, height)
        for (let i in rains) {
            let rain = rains[i]
            rain.update()
            rain.draw()
        }
    },1000 /60 )
}

export const freeRain = () => {
    clearInterval(timer)
}

export const initRainCanvas = (ctx1) => {
    ctx = ctx1
    start()
}
