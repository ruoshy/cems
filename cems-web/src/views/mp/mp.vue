<script setup>
import {
  NLayout,
  NLayoutSider,
  NLayoutHeader,
  NLayoutContent,
  NLayoutFooter,
  NMenu,
  NIcon,
  useMessage
} from "naive-ui"
import {h, resolveComponent, ref} from 'vue'
import {RouterLink} from 'vue-router'
import {useStore} from 'vuex'
window.$message = useMessage()
window.$store = useStore()

const menu = {
  current: ref(window.$store.state.menu.current),
  options: []
}
function renderIcon(icon) {
  return () => h('i', {
    class: 'iconfont ' + icon,
    style: 'font-size: 20px'
  })
}

function init() {
  let options = []
  window.$store.state.menu.items.forEach((item, index) => {
    options.push({
      label: () =>
          h(
              RouterLink,
              {
                to: {
                  name: item.key,
                }
              },
              {default: () => item.name}
          ),
      key: item.key,
      icon: renderIcon(item.icon)
    })
  })
  menu.options = options
}

function menuUpdate(key, item) {
  menu.current.value = key
  window.$store.commit('updateMenuSelected', key)
}

init()
</script>

<template>
  <n-layout style="height: 100%;">
    <n-layout-header style="background: #468DE9;padding: 0 24px;">
      <div style="line-height: 64px;color: white;">
        <i class="iconfont icon-icon_fabu" style="font-size: 33px;padding-right: 10px"/>
        <span style="font-weight: bold;font-size: 22px">CEMS</span>
      </div>
    </n-layout-header>
    <n-layout has-sider class="i-layout-content">
      <n-layout-sider width="200">
        <n-menu :options="menu.options" :on-update:value="menuUpdate" :value="menu.current.value"/>
      </n-layout-sider>
      <n-layout>
        <n-layout-content content-style="padding: 8px;">
          <router-view/>
        </n-layout-content>
      </n-layout>
    </n-layout>
  </n-layout>
</template>

<style scoped>
.n-layout-header {
  padding: 24px;
  height: 64px;
}

.n-layout-sider {
  background: white;
}

.n-layout-content {
  z-index: 100;
  position: relative;
  height: 100%;
  background: #F5F7F9;
}

.i-layout-content {
  height: calc(100% - 64px);
}
</style>

<style>
.n-menu .n-menu-item::before {
  left: 0;
  right: 0;
}

.n-menu-item--selected::after {
  content: '';
  display: block;
  width: 2px;
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  background: #2d8cf0;
}

.n-menu .n-menu-item {
  margin-top: 0;
  padding: 5px 0;
  font-weight: bold;
}
</style>
