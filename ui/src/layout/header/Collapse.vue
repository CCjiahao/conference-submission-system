<template>
    <component @click="clickBtn" :is="isCollapse ? MenuUnfoldOutlined : MenuFoldOutlined" class="icons" />
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons-vue'
import { collapseStore } from '@/store/collapse'

const store = collapseStore()
const isCollapse = computed(() => {
    return store.getCollapse
})

const clickBtn = () => {
    store.toggleCollapse()
}

</script>

<style scoped lang="scss">
.icons {
    font-size: 18px;
}
</style>