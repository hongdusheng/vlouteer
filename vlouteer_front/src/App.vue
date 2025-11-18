<script setup>
import { onErrorCaptured } from 'vue'
import { ElMessage } from 'element-plus'

// 全局错误处理
onErrorCaptured((error, instance, info) => {
  console.error('应用错误:', error)
  console.error('错误来源:', instance)
  console.error('错误信息:', info)
  ElMessage.error('应用发生错误，请刷新页面')
  return false // 阻止错误继续传播
})
</script>

<template>
  <router-view v-slot="{ Component }">
    <component :is="Component" v-if="Component" />
    <div v-else class="loading-container">
      <p>加载中...</p>
    </div>
  </router-view>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body, #app {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB',
    'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

#app {
  width: 100%;
  height: 100%;
}

/* 加载中容器样式 */
.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  font-size: 18px;
  color: #909399;
}

/* 全局滚动条样式 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-thumb {
  background-color: #c0c4cc;
  border-radius: 4px;
}

::-webkit-scrollbar-track {
  background-color: #f5f7fa;
}
</style>
