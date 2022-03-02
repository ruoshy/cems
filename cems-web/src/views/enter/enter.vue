<script setup>
import {NConfigProvider, NMessageProvider, useMessage, NCard, NButton, NInput, NSpace, NSelect} from "naive-ui"
import {useStore} from 'vuex'
import {useRouter} from 'vue-router'
import manage from "/src/api/manage"
import {reactive} from "vue";

window.$message = useMessage()
window.$store = useStore()
</script>

<template>
  <div>
    <div style="background: #4784e3;height: 70px">
      <div
          style="width: 500px;text-align: center;margin: 0 auto;line-height: 70px;color: white;font-size: 30px;font-weight: bold">
        考试网上报名平台
      </div>
    </div>
    <div style="width: 1000px;margin: 30px auto;border-radius: 10px;overflow: hidden">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>

</style>
