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

const waitForReviewPaperNumber: Ref<number> = ref(0);

onMounted(() => {
    const getPaperProcessDistribution = () => {
        GetPaperProcessDistributionApi().then((res: any) => {
            if (res.errno === 0) {
                waitForReviewPaperNumber.value = res.data[0]['value'];
                reviewProcessChart.setOption({
                    series: [
                        {
                            type: 'pie',
                            data: res.data,
                            radius: ['40%', '70%']
                        }
                    ],
                    tooltip: {//提示框组件
                        trigger: 'item', //item数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
                        axisPointer: {
                            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        },
                        formatter: '{b} : {c} <br/>百分比 : {d}%' //{b}（数据项名称），{c}（数值）, {d}（百分比）
                    }
                });
            }
        })
    }
    getPaperProcessDistribution();

    const getPaperExpertiseDistribution = () => {
        GetPaperExpertiseDistributionApi().then((res: any) => {
            if (res.errno === 0) {
                paperChart.setOption({
                    series: [{
                        type: 'pie',
                        data: res.data,
                        radius: ['40%', '70%']
                    }],
                    tooltip: {//提示框组件
                        trigger: 'item', //item数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用。
                        axisPointer: {
                            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        },
                        formatter: '{b} : {c} <br/>百分比 : {d}%' //{b}（数据项名称），{c}（数值）, {d}（百分比）
                    }
                });
            }
        })
    }
    getPaperExpertiseDistribution();

    const getPaperBySubmitTime = () => {
        GetPaperBySubmitTimeApi(30).then((res: any) => {
            if (res.errno === 0) {
                submissionChart.setOption({
                    tooltip: {
                        trigger: 'axis', //坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用
                        axisPointer: {// 坐标轴指示器，坐标轴触发有效
                            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        }
                    }, 
                    xAxis: { data: res.data['label'] },
                    yAxis: {},
                    series: [
                        {
                            name: "论文数量",
                            type: "line",
                            data: res.data['value'],
                            areaStyle: {}
                        },
                    ],
                });
            }
        })
    }
    getPaperBySubmitTime();


    const getPaperByReviewTime = () => {
        GetPaperByReviewTimeApi(30).then((res: any) => {
            if (res.errno === 0) {
                reviewNumChart.setOption({
                    tooltip: {
                        trigger: 'axis', //坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用
                        axisPointer: {// 坐标轴指示器，坐标轴触发有效
                            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                        }
                    }, 
                    xAxis: { data: res.data['label'] },
                    yAxis: {},
                    series: [
                        {
                            name: "审稿数量",
                            type: "line",
                            data: res.data['value'],
                            areaStyle: {
                                color: '#91cc75',
                                opacity: 0.5
                            },
                            itemStyle : {
								normal : {
									color:'#91cc75',
									lineStyle:{
										color:'#91cc75'
									}
								}
							},
                        },
                    ],
                });
            }
        })
    }
    getPaperByReviewTime();

    let submissionChart = echarts.init(document.getElementById("submissionChart") as HTMLElement);
    let reviewNumChart = echarts.init(document.getElementById("reviewNumChart") as HTMLElement);
    let reviewProcessChart = echarts.init(document.getElementById("reviewProcessChart") as HTMLElement);
    let paperChart = echarts.init(document.getElementById("paperChart") as HTMLElement);

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