<template>
    <div style="display: flex; justify-content: center; align-items: center; flex-direction: column;">
        <div>{{count}}</div>
        <a-button type="primary" @click="addBtn">新增</a-button>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { testStore } from '@/store/test';

const store = testStore()
const count = computed(() => {
    return store.getCount
})

// 按钮点击事件
const addBtn = () => {
    store.setCount()
}
</script>

<style scoped lang="scss">
</style>