<template>
    <h2>期刊主席首页</h2>
    <!-- 统计数据部分 -->
    <a-row :gutter="24">
        <a-col :span="6">
            <a-card>
                <a-statistic title="注册用户人数" :value="userNumber" />
            </a-card>
        </a-col>
        <a-col :span="6">
            <a-card>
                <a-statistic title="已投稿论文总数" :value="paperNumber" />
            </a-card>
        </a-col>
        <a-col :span="6">
            <a-card>
                <a-statistic title="昨日新增投稿数" :value="yesterdayPaperNumber" />
            </a-card>
        </a-col>
        <a-col :span="6">
            <a-card>
                <a-statistic title="当前待审核投稿数" :value="waitForReviewPaperNumber" />
            </a-card>
        </a-col>
    </a-row>

    <br />

    <!-- 图表部分 -->
    <a-row :gutter="24">
        <a-col :span="12">
            <a-card title="论文投稿数量分析">
                <div id="submissionChart" :style="{ width: '100%', height: '25vh' }"></div>
            </a-card>
        </a-col>
        <a-col :span="12">
            <a-card title="论文审稿数量分析">
                <div id="reviewNumChart" :style="{ width: '100%', height: '25vh' }"></div>
            </a-card>
        </a-col>
    </a-row>

    <br />

    <a-row :gutter="24">
        <a-col :span="12">
            <a-card title="论文审稿进度分析">
                <div id="reviewProcessChart" :style="{ width: '100%', height: '25vh' }"></div>
            </a-card>
        </a-col>
        <a-col :span="12">
            <a-card title="论文分类占比分析">
                <div id="paperChart" :style="{ width: '100%', height: '25vh' }"></div>
            </a-card>
        </a-col>
    </a-row>

</template>

<script setup lang="ts">
import { Ref, ref, reactive, computed } from 'vue'
import { onMounted } from "vue";
import * as echarts from 'echarts'
import { GetUserNumberApi, GetPaperNumberApi, GetYesterdayPaperNumberApi, GetPaperProcessDistributionApi, GetPaperExpertiseDistributionApi, GetPaperBySubmitTimeApi, GetPaperByReviewTimeApi } from '@/request/api';

var userNumber: Ref<number> = ref(0);
const getUserNumber = () => {
    GetUserNumberApi().then((res: any) => {
        if (res.errno === 0) {
            userNumber.value = res.data['userNumber'];
        }
    })
}
getUserNumber();

var paperNumber: Ref<number> = ref(0);
const getPaperNumber = () => {
    GetPaperNumberApi().then((res: any) => {
        if (res.errno === 0) {
            paperNumber.value = res.data['paperNumber'];
        }
    })
}
getPaperNumber();

var yesterdayPaperNumber: Ref<number> = ref(0);
const getYesterdayPaperNumber = () => {
    GetYesterdayPaperNumberApi().then((res: any) => {
        if (res.errno === 0) {
            yesterdayPaperNumber.value = res.data['yesterdayPaperNumber'];
        }
    })
}
getYesterdayPaperNumber();

const paperProcessDistribution: Ref<number[]> = ref([]);
const waitForReviewPaperNumber: Ref<number> = ref(0);

onMounted(() => {
    const getPaperProcessDistribution = () => {
        paperProcessDistribution.value = []
        GetPaperProcessDistributionApi().then((res: any) => {
            if (res.errno === 0) {
                console.log(res)
                paperProcessDistribution.value = res.data;
                waitForReviewPaperNumber.value = res.data[0]['value'];
                reviewProcessChart.setOption({
                    series: [
                        {
                            type: 'pie',
                            data: paperProcessDistribution.value,
                            radius: ['40%', '70%']
                        }
                    ]
                });
            }
        })
    }
    getPaperProcessDistribution();
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

</script>


<style scoped lang="scss">
</style>