<template>
<h2>文章详情</h2>
  <a-divider />
    <a-col :offset="1">
    <div style="width:550px">
    <br>
    <p><span style="font-weight:bold">论文编号:</span>{{ paper.id }}</p>
    <p><span style="font-weight:bold">论文标题:</span>{{ paper.title }}</p>
    <p><span style="font-weight:bold">论文分类:</span>{{paper.expertise}}</p>
    <p><span style="font-weight:bold">提交时间:</span>{{paper.commitTime}}</p>
    <p><span style="font-weight:bold">论文摘要:</span><br>{{ paper.abstracts }}</p>
    <br><br>
    </div>
    </a-col>
    <a-row>
        <a-col :span="3" :offset="1">
            <a-button type="primary" :size="size" @click="downPaperById(paper.paper)">
                <template #icon>
                    <DownloadOutlined />
                </template>
                下载论文
                </a-button>
        </a-col>
        <a-col :span="4">
            <a-button @click="goBack">返回</a-button>
        </a-col>
        <a-col :span="4">
            <a-button v-if="paper.state==='待审核'" type="primary" @click="goReview(paper.id)">审阅</a-button>
        </a-col>
    </a-row>
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue'
import { defineComponent } from 'vue';
import { GetPapersByIdApi } from '@/request/api';
import { DownloadOutlined } from '@ant-design/icons-vue';

export default defineComponent({
    components: {
        DownloadOutlined,
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
        },
        downPaperById(paperId:any){
            window.location.href = 'http://localhost:8081/api/download?uuid=' + paperId;
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