<template>
  <div>
    <h2><b>审稿人信息</b></h2>
  </div>
  <br>
  <a-descriptions title="你好，审稿人">
    <a-row type="flex" justify="space-between" align="bottom">
      <a-col :span="3">
        <div class="avatar">
          <a-avatar :size="64" style="background-color: #1890ff">{{ user.username }}</a-avatar>
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
  <h1><b>审核中的稿件</b></h1>
  <a-row :span="12" :wrap="true">
    <div class="toProcess" v-for="item in papers" v-bind:key="item" style="margin: 5px;">
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
      <a-descriptions-item label="投递流程" > 
                <a-timeline mode="left">
                <template v-if="paper.state==='待审核'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="blue">审阅</a-timeline-item>
                <a-timeline-item color="grey">辩驳</a-timeline-item>
                <a-timeline-item color="grey">再审阅</a-timeline-item>
                <a-timeline-item color="grey">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='待辩论'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="blue">辩驳</a-timeline-item>
                <a-timeline-item color="grey">再审阅</a-timeline-item>
                <a-timeline-item color="grey">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已辩论'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="blue">再审阅</a-timeline-item>
                <a-timeline-item color="grey">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已确认'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="green">再审阅</a-timeline-item>
                <a-timeline-item color="blue">最终结果</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已接收'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="green">再审阅</a-timeline-item>
                <a-timeline-item color="green">最终结果:已接收</a-timeline-item>
                </template>
                <template v-else-if="paper.state==='已拒绝'">
                <a-timeline-item color="green">提交</a-timeline-item>
                <a-timeline-item color="green">审阅</a-timeline-item>
                <a-timeline-item color="green">辩驳</a-timeline-item>
                <a-timeline-item color="green">再审阅</a-timeline-item>
                <a-timeline-item color="red">最终结果:已拒绝</a-timeline-item>
                </template>
                </a-timeline>
            </a-descriptions-item>
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

    <a-button type="primary" :href="'http://localhost:9875/api/download?uuid=' + paper.uuid" target="_blank">
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
    paperid.value = id;
    GetPaperDetailByIdApi(id).then((res: any) => {
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