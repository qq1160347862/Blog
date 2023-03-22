import axios from 'axios'
let service = axios.create({
    baseURL:"http://localhost:8086",
    timeout:8000,
})
export default service