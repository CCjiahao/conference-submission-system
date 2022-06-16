<template>
  <div style="margin-bottom: 20px">
    <h2>论文信息</h2>
  </div>
  <div>论文编号:{{ paper.id }}</div>
  <div>论文标题:{{ paper.title }}</div>
  <div>论文摘要:{{ paper.abstracts }}</div>
  <a :href="'http://101.35.144.217:9875/api/download?uuid=' + paper.paper" target="_blank">下载论文</a>
  <br>
  <a-divider />
  <div>
    <h2>评审问题</h2>
  </div>
  <a-col offset="1">
    <a-form :model="ReviewState" :label-col="labelCol" :wrapper-col="wrapperCol" layout='vertical'>
      <a-form-item label="1.是否符合本期刊议题?">
        <a-col offset="1">
          <a-radio-group v-model:value="ReviewState.isAssociated" name="radioGroup">
            <a-radio value="不符合">不符合</a-radio>
            <a-radio value="符合">符合</a-radio>
          </a-radio-group>
        </a-col>
      </a-form-item>
      <a-form-item label="2.请为论文逻辑性打分">
        <a-col :span="12" offset="1">
          <a-slider v-model:value="ReviewState.logic" :marks="marks" :included="true">
            <template #mark="{ label }">
              {{ label }}
            </template>
          </a-slider>
        </a-col>
      </a-form-item>
      <a-form-item label="3.请为论文科学性打分">
        <a-col :span="12" offset="1">
          <a-slider v-model:value="ReviewState.sci" :marks="marks" :included="true">
            <template #mark="{ label }">
              {{ label }}
            </template>
          </a-slider>
        </a-col>
      </a-form-item>
      <a-form-item label="4.请为论文创新性打分">
        <a-col :span="12" offset="1">
          <a-slider v-model:value="ReviewState.innovation" :marks="marks" :included="true">
            <template #mark="{ label }">
              {{ label }}
            </template>
          </a-slider>
        </a-col>
      </a-form-item>
      <a-form-item label="5.是否统一该论文通过">
        <a-col offset="1">
          <a-radio-group v-model:value="ReviewState.passOrReject" name="radioGroup">
            <a-radio value="强拒绝">强拒绝</a-radio>
            <a-radio value="弱拒绝">弱拒绝</a-radio>
            <a-radio value="弱接收">弱接收</a-radio>
            <a-radio value="强接收">强接收</a-radio>
          </a-radio-group>
        </a-col>
      </a-form-item>

      <a-form-item label="6.   您认为本篇论文的优缺点是什么？(即评审意见)">
        <a-col :span="12" offset="1">
          <a-textarea v-model:value="ReviewState.suggestion" placeholder="评审意见" :rows="4" />
        </a-col>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
        <a-button type="primary" @click="submitAndJump">提交</a-button>
        <a-button style="margin-left: 10px" @click="goBack">返回</a-button>
      </a-form-item>
    </a-form>
  </a-col>
</template>

<script lang="ts">

import { defineComponent, reactive, toRaw, ref } from 'vue';
import type { UnwrapRef } from 'vue';
import { GetPapersByIdApi,SubmitReviewApi } from '@/request/api';
import { message } from 'ant-design-vue';

interface ReviewState {
  paperId:string,
  isAssociated: string,
  logic: number,
  sci: number,
  innovation: number,
  passOrReject: string,
  suggestion: string,
}

const ReviewState = reactive<ReviewState>({
  paperId:'',
  isAssociated: '符合',
  logic: 33,
  sci: 33,
  innovation: 33,
  passOrReject: '弱拒绝',
  suggestion: '',
});

const token = localStorage.getItem('token');

export default defineComponent({
  components: {
  },
  data() {
    return {
      paper: []
    }
  },
  methods: {
    getPaperbyId(id: any) {
      GetPapersByIdApi(id).then((res: any) => {
        if (res.errno === 0) {
          this.paper = res.data['paper'];
          ReviewState.paperId = res.data['paper'].id;
        }
      })
    },
    goBack() {
      this.$router.replace({path:'/review/hall'});
    },
    submitAndJump(){
      if (token == null) {
        message.error("登录已过期");
        return;
      };
      if (ReviewState.suggestion === '') {
        message.error("评审意见内容不能为空");
        return;
      }
      SubmitReviewApi(token,ReviewState.paperId,ReviewState.isAssociated,ReviewState.logic,ReviewState.sci,ReviewState.innovation,ReviewState.passOrReject,ReviewState.suggestion).then((res:any)=>{
        message.info("评审意见提交成功")
        this.$router.replace({path:'/review/log'});
      }).catch((err:any)=>{
          console.log(err);
      });
    }
  },
  mounted() {
    let id = this.$route.query.id;
    this.getPaperbyId(id);
  },
  setup() {

    const marks = ref<Record<number, any>>({
      0: '很差',
      33: '一般',
      66: '良好',
      100: '优秀',
    });
    return {
      marks,
      labelCol: { style: { width: '400px' } },
      wrapperCol: { span: 14 },
      ReviewState,
    };
  },
})

</script>

<style scoped lang="scss">
</style>