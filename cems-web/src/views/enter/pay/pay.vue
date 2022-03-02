<script setup>
import QrcodeVue from 'qrcode.vue'
import {NCard, NButton, NInput, NSpace, NSelect, NImage, NCheckbox, NUpload} from "naive-ui"
import {useRouter} from 'vue-router'
import {reactive} from "vue";
import enter from "/src/api/enter"
import moment from 'moment'

const router = useRouter()
const exam = reactive({
  id: router.currentRoute.value.params.exam_id
})
const examinee = reactive({
  id: router.currentRoute.value.params.examinee_id
})

const order = reactive({
  id: router.currentRoute.value.params.order_id,
  price: router.currentRoute.value.params.money,
  date: router.currentRoute.value.params.date,
})

function pay() {
  enter.pay({exam_id: exam.id, examinee_id: examinee.id}).then(res => {
    let json = res.data
    if (json.data) {
      window.$message.info('报名成功')
      router.replace({name: 'enter_index'})
    }
  })
}
</script>

<template>
  <n-space vertical style="width: 1000px;margin: 0 auto;text-align: center">
    <qrcode-vue :value="order.id" size="300" level="H"/>
    <div style="text-align: left;display: inline-block">
      <p>订单号：{{ order.id }}</p>
      <p>费用：{{ order.price }}</p>
      <p>时间：{{ moment(order.date).format('YYYY-MM-DD HH:mm:ss') }}</p>
      <n-button style="width: 200px" @click="pay">支付</n-button>
    </div>
  </n-space>
</template>

<style scoped>

</style>
