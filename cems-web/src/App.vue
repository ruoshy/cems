<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
import {NConfigProvider, NMessageProvider} from "naive-ui"

const themeOverrides = {
  'Menu': {
    'itemTextColorHover': "#468DE9",
    'itemTextColorActive': '#468DE9',
    'itemTextColor': '#525A6C',
    'itemIconColor': '#525A6C',
    'itemIconColorHover': '#468DE9',
    'itemIconColorActive': '#468DE9',
    'borderRadius': '0',
    'itemColorActive': '#F2FAFE',
    'fontSize': '16px'
  }
}
</script>

<template>
  <n-config-provider :theme-overrides="themeOverrides" abstract>
    <n-message-provider>
      <router-view/>
    </n-message-provider>
  </n-config-provider>
</template>

<script>

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100%;
}

.n-input .n-input--resizable .n-input--stateful {
  --border-focus: 1px solid #2d8cf0;
  --border-hover: 1px solid #2d8cf0;
}
</style>
