<template>
  <div><h2>评审结果页</h2></div>
<div>逻辑性得分:{{review.logic}}</div>
<div>科学性得分:{{review.sci}}</div>
<div>创新性得分:{{review.innovation}}</div>
<div>接收意见:{{review.passOrReject}}</div>
<div>评审意见:{{review.suggestion}}</div>
<a-button type="primary" @click="goBack">返回</a-button>
</template>

<script lang="ts">
import { ref, reactive, computed } from 'vue'
import { defineComponent } from 'vue';
import axios from 'axios';

export default defineComponent({
    components: {
  },
    data(){
        return{
            review:[]
        }
    },
    methods:{
        getReviewbyPaperId(id:any){
            axios({
              url:"http://localhost:8081/api/getReviewByPaperId",
              params:{'paperid':id},
              method:'GET',
          }).then(res=>{
              console.log(res.data);
              this.review = res.data.data['review'];
          })
        },
        goBack(){
            this.$router.go(-1);
        },
        goReview(id:any){
            this.$router.push({path:"/review/edit",query:{id:id}});
        }
    },
    mounted() {
        console.log(this.$route.query.id);
        let id = this.$route.query.id;
        this.getReviewbyPaperId(id);
    }
})

</script>

<style>

</style>