<template>
    <div class="logo">
        <img src="@/assets/logo.png" />
        <span v-show="show" class="title">ECNU期刊投稿系统</span>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { collapseStore } from '@/store/collapse';

// 折叠
const store = collapseStore()
const collapsed = computed(() => {
    return store.getCollapse
})

// 缓慢的折叠
const show = ref(true);
watch(() => collapsed.value, (collapsed: boolean) => {
    if (!collapsed) {
        setTimeout(() => {
            show.value = !collapsed
        }, 150)
    } else {
        show.value = !collapsed
    }
})

</script>

<style scoped lang='scss'>
.logo {
    height: 50px;
    border: none;
    line-height: 50px;
    display: flex;
    align-items: center;
    padding-left: 18px;
    color: #ffffff;

    img {
        width: 25px;
        height: 25px;
        margin-left: 7px;
    }

    .title {
        height: 50px;
        text-align: center;
        color: #ffffff;
        box-sizing: border-box;
        font-weight: 600;
        line-height: 50px;
        font-size: 13px;
        vertical-align: middle;
    }
}
</style>