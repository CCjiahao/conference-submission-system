<template>
  <div>
    <h2>审稿人信息</h2>
  </div>
  <br>
  <a-descriptions title="你好，审稿人">
    <a-row type="flex" justify="space-between" align="bottom">
      <a-col :span="3">
        <div class="avatar">
          <a-avatar :size="64">
            <template #icon>
              <UserOutlined />
            </template>
          </a-avatar>
        </div>
      </a-col>
      <a-col :span="13">
        <a-row>
          <a-col :span="8">姓名:{{ user.name }}</a-col>
          <a-col :span="8">国家:{{ user.country }}</a-col>
          <a-col :span="8">所属组织:{{ user.school }}</a-col>
        </a-row>
        <a-row>
          <a-col :span="8">邮箱:{{ user.email }}</a-col>
          <a-col :span="8">所属组别:{{ user.expertise }}</a-col>
        </a-row>

      </a-col>
      <a-col :span="8">
        <a-row>
          <h2>审稿数:{{ numOfReviewed }}</h2>
          <a-divider type="vertical" style="height: 35px; background-color: #7cb305" />
          <h2>待审稿数:{{ numOfReview }}</h2>
        </a-row>
      </a-col>
    </a-row>
  </a-descriptions>
  <br>
  <h1>审核中的稿件</h1>
  <a-row :span="12" :wrap="true">
    <div class="toProcess" v-for="item in papers" v-bind:key="item">
      <a-col :span="4">
        <a-card
          style="width: 300px; height:197px; white-space: no-warp; display:inline-block; overflow:hidden; text-overflow:ellipsis">
          <template #title>{{ item.title }}</template>
          <template #extra>
            <a @click="showDrawer(item.id)">查看</a>
          </template>
          <p>{{ item.abstracts }}</p>
        </a-card>
      </a-col>

    </div>
  </a-row>
  <a-drawer width="800" placement="right" :closable="false" :visible="visible" @close="onClose">
    <a-descriptions title="论文详情" bordered>
      <a-descriptions-item label="论文标题">{{ paper.title }}</a-descriptions-item>
      <a-descriptions-item label="所属领域">{{ paper.expertise }}</a-descriptions-item>
      <a-descriptions-item label="论文状态">{{ paper.state }}</a-descriptions-item>
      <a-descriptions-item label="论文摘要" :span="3">{{ paper.abstracts }}</a-descriptions-item>
      <a-descriptions-item label="提交时间">{{ paper.commitTime }}</a-descriptions-item>
    </a-descriptions>
    <a-divider />
    <b>作者详情</b>
    <a-list :data-source="paper.authors">
      <template #renderItem="{ item }">
        <a-descriptions :title="item.username" bordered>
          <a-descriptions-item label="姓名">{{ item.name }}</a-descriptions-item>
          <a-descriptions-item label="学校/组织">{{ item.school }}</a-descriptions-item>
          <a-descriptions-item label="国家">{{ item.country }}</a-descriptions-item>
          <a-descriptions-item label="邮箱">{{ item.email }}</a-descriptions-item>
        </a-descriptions>
        <a-divider />
      </template>
    </a-list>

    <a-button type="primary" :href="'http://localhost:8081/api/download?uuid=' + paper.uuid" target="_blank">
      <template #icon>
        <DownloadOutlined />
      </template>
      下载论文
    </a-button>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { userStore } from '../../store/user';
import { GetPapersByReviewerApi, GetReviewedPapersByReviewerApi, GetPaperDetailByIdApi } from '@/request/api';

const store = userStore();
const user = store.getState;

const numOfReview = ref()
const papers = ref()
const numOfReviewed = ref()
const token = localStorage.getItem('token');
if (token) {
  GetPapersByReviewerApi(token).then((res: any) => {
    if (res.errno === 0) {
      numOfReview.value = res.data['papers'].length;
    }
  });
  GetReviewedPapersByReviewerApi(token).then((res: any) => {
    if (res.errno === 0) {
      papers.value = res.data['papers'];
      numOfReviewed.value = res.data['papers'].length;
    }
  });
}

const paper = ref({
    uuid: '',
    title: '',
    abstracts: '',
    expertise: '',
    commitTime: '',
    state: '',
    authors: [
        {
            username: '',
            name: '',
            email: '',
            school: '',
            country: '',
        }
    ]
})
const paperid = ref(0);
const visible = ref<boolean>(false);
const showDrawer = (id: number) => {
    console.log(id)
    paperid.value = id;
    GetPaperDetailByIdApi(id).then((res: any) => {
        console.log(res)
        if (res.errno === 0) {
            paper.value = res.data['detail'];
            visible.value = true;
        }
    })
};
const onClose = () => {
    visible.value = false;
};

</script>

<style scoped lang="scss">
</style>