<template>
<h2>期刊主席首页</h2>
    <!-- 统计数据部分 -->
    <a-row :gutter="24">
      <a-col :span="6">
        <a-card>
          <a-statistic title="Active Users" :value="112893" style="margin-right: 50px" />
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="Account Balance (CNY)" :precision="2" :value="112893" />
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="Active Users" :value="112893" style="margin-right: 50px" />
        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card>
          <a-statistic title="Account Balance (CNY)" :precision="2" :value="112893" />
        </a-card>
      </a-col>
    </a-row>

    <br />

    <!-- 图表部分 -->
    <a-row :gutter="24">
    <a-col :span="12">
        <a-card title="论文投稿数量分析">
            <div id="submissionChart" :style="{width: '100%', height: '25vh'}"></div>
        </a-card>
    </a-col>
        <a-col :span="12">
        <a-card title="论文审稿数量分析">
            <div id="reviewNumChart" :style="{width: '100%', height: '25vh'}"></div>
        </a-card>
    </a-col>
    </a-row>

    <br />

    <a-row :gutter="24">
    <a-col :span="12">
        <a-card title="论文审稿进度分析">
            <div id="reviewProcessChart" :style="{width: '100%', height: '25vh'}"></div>
        </a-card>
    </a-col>
        <a-col :span="12">
        <a-card title="论文分类占比分析">
            <div id="paperChart" :style="{width: '100%', height: '25vh'}"></div>
        </a-card>
    </a-col>
    </a-row>

</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue'
import { onMounted } from "vue";
import * as echarts from 'echarts'

export default {
  name: "data_page",
  setup() {
    onMounted(() => {//需要获取到element,所以是onMounted的Hook
        let submissionChart = echarts.init(document.getElementById("submissionChart") as HTMLElement);
        let reviewNumChart = echarts.init(document.getElementById("reviewNumChart") as HTMLElement);
        let reviewProcessChart = echarts.init(document.getElementById("reviewProcessChart") as HTMLElement);
        let paperChart = echarts.init(document.getElementById("paperChart") as HTMLElement);

        // 绘制图表
        submissionChart.setOption({
            tooltip: {},
            xAxis: {
                data: ["12-3", "12-4", "12-5", "12-6", "12-7", "12-8"],
            },
            yAxis: {},
            series: [
            {
                name: "用户量",
                type: "line",
                data: [5, 20, 36, 10, 10, 20],
                areaStyle: {}
            },
            ],
        });

        reviewNumChart.setOption({
            tooltip: {},
            xAxis: {
                data: ["12-3", "12-4", "12-5", "12-6", "12-7", "12-8"],
            },
            yAxis: {},
            series: [
            {
                name: "用户量",
                type: "line",
                data: [5, 20, 36, 10, 10, 20],
                areaStyle: {}
            },
            ],
        });

        reviewProcessChart.setOption({
            series: [
                {
                    type: 'pie',
                    data: [
                        {
                        value: 335,
                        name: 'A'
                        },
                        {
                        value: 234,
                        name: 'B'
                        },
                        {
                        value: 1548,
                        name: 'C'
                        }
                    ],
                    radius: ['40%', '70%']
                }
            ]
        });

        paperChart.setOption({
            series: [
                {
                    type: 'pie',
                    data: [
                        {
                        value: 335,
                        name: 'A'
                        },
                        {
                        value: 234,
                        name: 'B'
                        },
                        {
                        value: 1548,
                        name: 'C'
                        }
                    ],
                    radius: ['40%', '70%']
                }
            ]
        });

      window.onresize = function () {//自适应大小
        submissionChart.resize();
        reviewNumChart.resize();
        reviewProcessChart.resize();
        paperChart.resize();
      };
    });
  },
  components: {},
  mounted() {},
};

</script>


<style scoped lang="scss">
</style>