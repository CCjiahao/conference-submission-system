<template>
    <div class="header-bg">
        <Collapse></Collapse>
    </div>
    <div class="header-bg">
        <Avater></Avater>
    </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue';
import Avater from "./Avater.vue"
</script>

<style scoped lang="scss">
.header-bg {
    display: flex;
    align-items: center;
}
</style>