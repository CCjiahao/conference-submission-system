<template>
<h2>审稿记录</h2>
  <a-table :columns="columns" :data-source="data">
    <template #headerCell="{ column }">
      <template v-if="column.key === 'id'">
        <span>
          论文编号
        </span>
      </template>
    </template>

    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'title'">
          <!--每篇文章都有对应的detail页，辩论页，编辑审阅页-->
        <router-link to="/review/detail">
          {{ record.title }}
        </router-link>
      </template>
      <template v-else-if="column.key === 'action'">
        <span>
            <!--下载请求-->
          <a>下载文章</a>
          <a-divider type="vertical" />
          <router-link to="/review/edit">编辑审阅</router-link>
          <a-divider type="vertical" />
          <a>查看辩论</a>
        </span>
      </template>
    </template>
  </a-table>
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue';
import { SmileOutlined, DownOutlined } from '@ant-design/icons-vue';
import { defineComponent } from 'vue';

const columns = [
  {
    name: '论文编号',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '论文标题',
    dataIndex: 'title',
    key: 'title',
  },
  {
    title: '提交时间',
    dataIndex: 'commit_time',
    key: 'commit_time',
  },
  {
    title: '状态',
    key: 'state',
    dataIndex: 'state',
  },
  {
    title: '操作',
    key: 'action',
  },
];

const data = [
  {
      id:'1',
      title:'第一篇论文',
      commit_time:'12:00',
      state:'待审阅'
  },
  {
      id:'2',
      title:'第二篇论文',
      commit_time:'12:00',
      state:'待审阅'
  },
  {
      id:'3',
      title:'第三篇论文',
      commit_time:'12:00',
      state:'待审阅'
  },
  {
      id:'4',
      title:'第四篇论文',
      commit_time:'12:00',
      state:'待审阅'
  },
  {
      id:'5',
      title:'第五篇论文',
      commit_time:'12:00',
      state:'待审阅'
  }
];

export default defineComponent({
  components: {
    SmileOutlined,
    DownOutlined,
  },
  setup() {
    return {
      data,
      columns,
    };
  },
});
</script>

<style scoped lang="scss">
</style>