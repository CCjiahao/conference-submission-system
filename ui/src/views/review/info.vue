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
      <h2>审稿数:{{numOfReviewed}}</h2>
      <a-divider type="vertical" style="height: 35px; background-color: #7cb305" />
      <h2>待审稿数:{{numOfReview}}</h2>
      </a-row>
  </a-col>
</a-row>
</a-descriptions>
<br>
<h1>审核中的稿件</h1>
<a-row :span="12" :wrap="true">
<div class="toProcess" v-for="item in papers" v-bind:key="item">
<a-col :span="4">
<a-card  style="width: 300px; height:200px">
<template #title>{{item.title}}</template>
<template #extra><router-link :to="{ path: '/review/detail', query: { id: item.id } }">查看</router-link></template>
<p>{{item.abstracts}}</p>
</a-card>
</a-col>

</div>
</a-row>

</template>

<script lang="ts">
import { ref, reactive, computed,defineComponent } from 'vue';
import { userStore } from '../../store/user';
import { useRouter } from 'vue-router';
import { GetPapersByReviewerApi,GetReviewedPapersByReviewerApi } from '@/request/api';

const store = userStore();
const user = store.getState;

export default defineComponent({
  components:{
  },
  data(){
    return {
      // 待审稿件数
      // 审稿数
      numOfReview:0,
      numOfReviewed:0,
      // 审核中的论文
      papers:[]
    }
  },
  methods:{
    getPapers() {
            const token = localStorage.getItem('token');
            if (token) {
                GetPapersByReviewerApi(token).then((res: any) => {
                    if (res.errno === 0) {
                        this.numOfReview = res.data['papers'].length;
                    }
                });
                GetReviewedPapersByReviewerApi(token).then((res: any) => {
                    if (res.errno === 0) {
                        this.papers = res.data['papers'];
                        this.numOfReviewed = res.data['papers'].length;
                    }
                });
            }
        }
  },
  mounted(){
    this.getPapers();
  },
  setup(){
    return{
      user
    }
  }
});

</script>

<style scoped lang="scss">
</style>