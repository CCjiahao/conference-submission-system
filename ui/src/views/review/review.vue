<template>
    <div>
        <h2>评审结果页</h2>
    </div>

    <a-form :model="paperState">
        <div style="margin: 5px 0px;">逻辑性得分: {{ review.logic }}</div>
        <div style="margin: 5px 0px;">科学性得分: {{ review.sci }}</div>
        <div style="margin: 5px 0px;">创新性得分: {{ review.innovation }}</div>
        <div style="margin: 5px 0px;">接收意见: {{ review.passOrReject }}</div>
        <div style="margin: 5px 0px;">评审意见: {{ review.suggestion }}</div>
        <a-form-item name="rebuttal" @v-if="author">
            <a-textarea v-model:value="paperState.rebuttal" placeholder="你的辩论内容" />
        </a-form-item>
        <a-button type="primary" @click="onFinish" style="margin-top: 10px" @v-if="author">提交辩论结果</a-button>
        <a-button @click="goBack" style="margin-top: 10px">返回</a-button>
    </a-form>
</template>

<script lang="ts">
import { defineComponent, reactive } from 'vue';
import { GetReviewByPaperIdApi } from '@/request/api'
import { message } from 'ant-design-vue';
import { SubmitRebuttalApi } from '@/request/api'

interface PaperState {
    rebuttal: string;
}
export default defineComponent({
    data() {
        return {
            review: [],
            paperState: { rebuttal: '' },
            author: false
        }
    },
    methods: {
        getReviewbyPaperId(id: any) {
            GetReviewByPaperIdApi(id).then((res: any) => {
                if (res.errno === 0) {
                    this.review = res.data['review'];
                }
            })
        },
        goBack() {
            this.$router.go(-1);
        },
        onFinish(values: any) {
            if (this.paperState.rebuttal === '') {
                message.error("辩驳内容不能为空")
                return;
            }
            const token = localStorage.getItem('token');
            console.log(this.review)
            if (token) {
                SubmitRebuttalApi(token, this.review.reviewer, this.review.paperId, this.paperState.rebuttal).then((res: any) => {
                    if (res.errno === 0) {
                        message.info("rebuttal提交成功！")
                        this.goBack()
                    }
                })
            }
        }
    },
    mounted() {
        let id = this.$route.query.id;
        this.getReviewbyPaperId(id);
        this.paperState = reactive<PaperState>({
            rebuttal: '',
        });
        this.author = this.$route.name === "submission/rebuttal";
    }
})

</script>

<style>
</style>