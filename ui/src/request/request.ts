import axios from "axios";
import { message } from 'ant-design-vue';


// 创建实例
const instance = axios.create({
    timeout: 15000,
    baseURL: 'http://101.35.144.217:9875'
})

// 请求拦截器
instance.interceptors.request.use(config => {
    return config;
}, err => {
    return Promise.reject(err)
})

// 相应拦截器
instance.interceptors.response.use(result => {
    const data = result.data;
    if (data.errno === 1) {
        message.error(data.errmsg || '网络请求错误');
    }
    return data;
}, err => {
    return Promise.reject(err)
})

export default instance