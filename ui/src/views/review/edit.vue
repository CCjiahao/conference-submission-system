<template>
  <h2>编辑审阅</h2>
<div>论文编号:{{paper.id}}</div>
<div>论文标题:{{paper.title}}</div>
<div>论文摘要:{{paper.abstracts}}</div>
<a :href="'http://localhost:8081/api/download?uuid='+paper.paper" target="_blank">下载论文</a>
<br>
<a-button type="primary" @click="goBack">返回</a-button>
<a-button type="primary" >提交</a-button>
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
            paper:[]
        }
    },
    methods:{
        getPaperbyId(id:any){
            axios({
              url:"http://localhost:8081/api/getPaperById",
              params:{'id':id},
              method:'GET',
          }).then(res=>{
              console.log(res.data);
              this.paper = res.data;
          })
        },
        goBack(){
            this.$router.go(-1);
        },
        goReview(id:any){
            this.$router.push({path:"/review/edit",params:{id:id}});
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