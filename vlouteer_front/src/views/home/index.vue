<template>
  <div class="home-container">
    <div class="welcome-section">
      <h2 class="page-title">高考志愿填报系统</h2>
      <div class="steps-section">
        <p class="steps-title">同学们登录系统后控制以下步骤进行操作</p>
        <div class="steps">
          <div class="step-item">
            <div class="step-number">1</div>
            <div class="step-content">登录系统，在院校查询页面查询自己想报考的专业和分数线。</div>
          </div>
          <div class="step-item">
            <div class="step-number">2</div>
            <div class="step-content">填写个人相关信息。</div>
          </div>
          <div class="step-item">
            <div class="step-number">3</div>
            <div class="step-content">可在"测评分析"页面测评个人的志愿方向。</div>
          </div>
          <div class="step-item">
            <div class="step-number">4</div>
            <div class="step-content">填报个人志愿。</div>
          </div>
        </div>
      </div>
    </div>

    <div class="content-section">
      <div class="notice-section">
        <div class="section-header">
          <h3>公告</h3>
        </div>
        <div class="notice-content">
          <ul class="notice-list">
            <li>1 志愿第一批填报已经开启，欢迎同学们进入系统填报。</li>
            <li>2 填报过程中有什么不理解，请联系系统管理员。</li>
          </ul>
        </div>
      </div>

      <div class="contact-section">
        <div class="section-header">
          <h3>联系信息</h3>
        </div>
        <div class="contact-content">
          <p><el-icon><Message /></el-icon> QQ: 222525250</p>
          <p><el-icon><ChatDotSquare /></el-icon> 微信: 12345678910</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Message, ChatDotSquare } from '@element-plus/icons-vue'
</script>

<style scoped>
.home-container {
  padding: 10px;
}

.welcome-section {
  margin-bottom: 30px;
}

.page-title {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.steps-title {
  font-size: 16px;
  color: #666;
  margin-bottom: 20px;
}

.steps {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.step-item {
  display: flex;
  align-items: flex-start;
}

.step-number {
  width: 24px;
  height: 24px;
  background-color: #409EFF;
  color: white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  margin-right: 15px;
  flex-shrink: 0;
}

.step-content {
  font-size: 16px;
  color: #333;
}

.content-section {
  display: flex;
  gap: 30px;
}

.notice-section, .contact-section {
  flex: 1;
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.section-header {
  margin-bottom: 15px;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.section-header h3 {
  font-size: 18px;
  color: #333;
  margin: 0;
}

.notice-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.notice-list li {
  margin-bottom: 10px;
  padding-left: 5px;
  font-size: 14px;
  color: #666;
}

.contact-content {
  font-size: 14px;
  color: #666;
}

.contact-content p {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.contact-content .el-icon {
  margin-right: 8px;
  color: #409EFF;
}
</style> 