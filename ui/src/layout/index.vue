<template>
  <a-layout class="layout">
    <a-layout-sider v-model:collapsed="collapsed" :trigger="null" class="asside">
      <menu-bar></menu-bar>
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="header">
        <Header></Header>
      </a-layout-header>
      <a-layout-content class="main">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import MenuBar from './menu/MenuBar.vue';
import Header from './header/Header.vue';
import { collapseStore } from '@/store/collapse';

const store = collapseStore()
const collapsed = computed(() => {
  return store.getCollapse
})

</script>

<style scoped lang="scss">
.layout {
  background-color: #001529;
  min-height: 100vh;

  .asside {
    background-color: #001529;
    border-right: 1px solid #f0f0f0 !important;
  }
  .header {
    height: 50px;
    padding: 0px 20px;
    margin-bottom: 10px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #ffffff;
  }
  .main {
    padding: 20px !important;
    background-color: #ffffff !important;
  }
}

</style>