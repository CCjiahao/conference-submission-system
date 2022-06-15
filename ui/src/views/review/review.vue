<template>
    <div>
        <h2>评审结果</h2>
    </div>
    <a-form :model="paperState">
        <div style="margin: 5px 0px;">逻辑性得分: {{ review.logic }}</div>
        <div style="margin: 5px 0px;">科学性得分: {{ review.sci }}</div>
        <div style="margin: 5px 0px;">创新性得分: {{ review.innovation }}</div>
        <div style="margin: 5px 0px;">接收意见: {{ review.passOrReject }}</div>
        <div style="margin: 5px 0px;">评审意见: {{ review.suggestion }}</div>
        <a-row style="width: 100%;" v-if="author || paperState.rebuttal !== ''">
            <a-col>辩驳意见:</a-col>
            <a-col :span="20" style="margin-left: 10px;">
                <a-form-item name="rebuttal">
                    <a-textarea v-model:value="paperState.rebuttal" :disabled="disabled" placeholder="你的辩论内容" />
                </a-form-item>
            </a-col>
        </a-row>
    </a-form>
    <a-row style="margin-top: 10px">
        <a-col v-if="!chairman">
            <a-button type="primary" @click="onFinish" v-if="author && !disabled">提交辩论结果</a-button>
        </a-col>
        <a-col v-if="!chairman">
            <div v-if="!author && paperState.rebuttal !== ''">
                <a-button type="danger" ghost @click="showModal1">修改评审意见</a-button>
                <a-button type="primary" @click="showModal">确认最终意见</a-button>
                <a-modal v-model:visible="visible" title="确认最终意见" @ok="submitConfirmReview" okText="确认" cancelText="取消">
                    <p>是否确认为最终的评审意见？</p>
                </a-modal>
                <a-modal v-model:visible="visible1" title="修改评审意见" @ok="saveReview" okText="保存" cancelText="取消">
                    <a-form :model="ReviewState">
                        <a-form-item label="逻辑性">
                            <a-slider v-model:value="ReviewState.logic"></a-slider>
                        </a-form-item>
                        <a-form-item label="科学性">
                            <a-slider v-model:value="ReviewState.sci"></a-slider>
                        </a-form-item>
                        <a-form-item label="创新性">
                            <a-slider v-model:value="ReviewState.innovation"></a-slider>
                        </a-form-item>
                        <a-form-item label="通过建议">
                            <a-radio-group v-model:value="ReviewState.passOrReject" name="radioGroup">
                                <a-radio value="强拒绝">强拒绝</a-radio>
                                <a-radio value="弱拒绝">弱拒绝</a-radio>
                                <a-radio value="弱接收">弱接收</a-radio>
                                <a-radio value="强接收">强接收</a-radio>
                            </a-radio-group>
                        </a-form-item>
                        <a-form-item label="评审意见">
                            <a-textarea v-model:value="ReviewState.suggestion" placeholder="评审意见" :rows="4" />
                        </a-form-item>
                    </a-form>
                </a-modal>
            </div>
        </a-col>
        <a-col>
            <a-button @click="goBack">返回</a-button>
        </a-col>
    </a-row>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from 'vue';
import { GetReviewByPaperIdApi, SubmitRebuttalApi, GetRebuttalByPaperIdApi, UpdateReviewApi, ConfirmReviewApi } from '@/request/api'
import { message } from 'ant-design-vue';

interface PaperState {
    rebuttal: string;
};

export default defineComponent({
    data() {
        return {
            review: [],
            paperState: reactive({ rebuttal: '' }),
            ReviewState: {
                paperId: '',
                logic: 33,
                sci: 33,
                innovation: 33,
                passOrReject: '弱拒绝',
                suggestion: '',
            },
            author: false,
            chairman: false,
            disabled: false,
        }
    },
    methods: {
        getReviewbyPaperId(id: any) {
            GetReviewByPaperIdApi(id).then((res: any) => {
                if (res.errno === 0) {
                    this.review = res.data['review'];
                    // 深拷贝
                    this.ReviewState = JSON.parse(JSON.stringify(res.data['review']));
                }
            })
        },
        saveReview() {
            if (this.ReviewState.suggestion === '') {
                message.error("评审建议不能为空")
                return;
            }
            this.review = JSON.parse(JSON.stringify(this.ReviewState));
            const token = localStorage.getItem('token');
            if (token) {
                UpdateReviewApi(token, this.review.paperId, this.review.logic, this.review.sci, this.review.innovation, this.review.passOrReject, this.review.suggestion).then((res: any) => {
                    if (res.errno === 0) {
                        message.info("评审意见修改成功！")
                        this.visible = false;
                        this.visible1 = false;
                    }
                })
            }

        },
        submitConfirmReview() {
            const token = localStorage.getItem('token');
            if (token) {
                ConfirmReviewApi(token, this.review.paperId).then((res: any) => {
                    if (res.errno === 0) {
                        message.info("最终意见确认成功！")
                        this.visible = false;
                        this.goBack();
                    }
                })
            }
        },
        getRebuttalbyPaperId(id: any) {
            GetRebuttalByPaperIdApi(id).then((res: any) => {
                this.disabled = (res.errno === 0);
                if (res.errno === 0) {
                    this.paperState.rebuttal = res.data.rebuttal.rebuttal;
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
        this.getRebuttalbyPaperId(id);
        this.paperState = reactive<PaperState>({
            rebuttal: '',
        });
        this.author = this.$route.name === "submission/rebuttal";
        this.chairman = this.$route.name === "chairman_review";
    },
    setup() {
        const visible = ref<boolean>(false);
        const visible1 = ref<boolean>(false);
        const showModal = () => {
            visible.value = true;
        };
        const showModal1 = () => {
            visible1.value = true;
        }
        return {
            visible,
            visible1,
            showModal,
            showModal1,
        };
    },
})

</script>

<style>
</style>