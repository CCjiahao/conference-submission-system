<template>
    <h2>文章详情</h2>
    <div>论文编号:{{ paper.id }}</div>
    <div>论文标题:{{ paper.title }}</div>
    <div>论文摘要:{{ paper.abstracts }}</div>
    <a :href="'http://localhost:8081/api/download?uuid=' + paper.paper" target="_blank">下载论文</a>
    <br>
    <a-button type="primary" @click="goBack">返回</a-button>
    <a-button type="primary" @click="goReview(paper.id)">审阅</a-button>
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue'
import { defineComponent } from 'vue';
import { GetPapersByIdApi } from '@/request/api';

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
                console.log(res);
                if (res.errno === 0) {
                    this.paper = res.data['paper'];
                }
            })
        },
        goBack() {
            this.$router.go(-1);
        },
        goReview(id: any) {
            this.$router.push({ path: "/review/edit", query: { id: id } });
        }
    },
    mounted() {
        console.log(this.$route.query.id);
        let id = this.$route.query.id;
        this.getPaperbyId(id);
    }
})

</script>

<style scoped lang="scss">
</style>