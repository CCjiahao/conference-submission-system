import { createApp } from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import * as Icons from '@ant-design/icons-vue'
import router from './router'
import { createPinia } from 'pinia'

const app = createApp(App)

// 注册图标
Object.keys(Icons).forEach((key) => {
    app.component(key, Icons[key as keyof typeof Icons])
})

app.use(createPinia()).use(router).use(Antd).mount('#app')
