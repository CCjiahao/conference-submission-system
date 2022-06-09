<template>
<div><h2>审稿人信息</h2></div>
<br>
<a-descriptions title="你好，审稿人">
 <a-row type="flex" justify="space-between" align="bottom">
  <a-col :span="3">
      <div class="avatar">
        <a-avatar :size="64">
        <template #icon><UserOutlined /></template>
        </a-avatar>
    </div>
  </a-col>
  <a-col :span="13">
      <a-row>
        <a-col :span="8">姓名:{{user.name}}</a-col>
        <a-col :span="8">国家:{{user.country}}</a-col>
        <a-col :span="8">所属组织:{{user.school}}</a-col>
    </a-row>
    <a-row>
        <a-col :span="8">邮箱:{{user.email}}</a-col>
        <a-col :span="8">所属组别:{{user.expertise}}</a-col>
    </a-row>

  </a-col>
  <a-col :span="8">
      <a-row>
      <h2>审稿数:20</h2>
      <a-divider type="vertical" style="height: 35px; background-color: #7cb305" />
      <h2>待审稿数:10</h2>
      </a-row>
  </a-col>
</a-row>
</a-descriptions>
<br>
<h1>审核中的稿件</h1>
<div class="toProcess">
<a-row :gutter="[8,16]">
  <a-col :span="4" :offset="3">
      <a-card title="第一篇论文" style="width: 300px">
    <template #extra><a href="#">查看</a></template>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
  </a-card>
  </a-col>
  <a-col :span="4">
      <a-card title="第二篇论文" style="width: 300px">
    <template #extra><a href="#">查看</a></template>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
  </a-card>
  </a-col>
  <a-col :span="4">
      <a-card title="第三篇论文" style="width: 300px">
    <template #extra><a href="#">查看</a></template>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
  </a-card>
  </a-col>
</a-row>
<a-row :gutter="[8,16]">
  <a-col :span="4" :offset="3">
      <a-card title="第四篇论文" style="width: 300px">
    <template #extra><a href="#">查看</a></template>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
  </a-card>
  </a-col>
  <a-col :span="4">
      <a-card title="第五篇论文" style="width: 300px">
    <template #extra><a href="#">查看</a></template>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
  </a-card>
  </a-col>
  <a-col :span="4">
      <a-card title="第六篇论文" style="width: 300px">
    <template #extra><a href="#">查看</a></template>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
    <p>这是一段描述</p>
  </a-card>
  </a-col>
</a-row>
</div>


</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { userStore } from '../../store/user';
import { useRouter } from 'vue-router';

const store = userStore()
const user = store.getState

</script>

<style scoped lang="scss">
</style>