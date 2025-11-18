import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import router from './router'
import { setupMock } from './mock'

import './assets/main.css'

// 初始化模拟服务器
setupMock()

// 创建应用实例
const app = createApp(App)

// 全局错误处理
app.config.errorHandler = (err, vm, info) => {
  console.error('全局错误:', err)
  console.error('错误组件:', vm)
  console.error('错误信息:', info)
}

// 未捕获的Promise错误处理
window.addEventListener('unhandledrejection', event => {
  console.error('未处理的Promise错误:', event.reason)
})

// 全局错误处理
window.onerror = function (message, source, lineno, colno, error) {
  console.error('全局JavaScript错误:', {
    message,
    source,
    lineno,
    colno,
    error
  })
}

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 使用插件
app.use(ElementPlus, {
  size: 'default'
})
app.use(router)

// 挂载应用
app.mount('#app')
