<template>
  <el-container>
    <el-container>
      <el-aside width="240px" style="background-color: rgb(255, 255, 255)">
        <el-menu
          default-active="1000"
          class="el-menu-vertical-demo"
          :default-openeds="['2000']"
          @select="selectMenu">
          <el-menu-item index="1000">申请基本信息</el-menu-item>
          <el-submenu index="2000">
            <template slot="title">
              <span>担保信息</span>
            </template>
            <el-menu-item index="20001010">新增的担保信息</el-menu-item>
            <el-menu-item index="20001020">引入的担保信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <business-apply :applyid="curapplyid" v-if="curnodeid == '1000'"></business-apply>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import BusinessApply from '@/views/business/apply/businessapply'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'BusinessApplyTreeView',
  components: { BusinessApply },
  directives: { waves },
  filters: {},
  props: {
    applyid: String
  },
  data() {
    return {
      curapplyid: this.applyid,
      curnodeid: '1000'
    }
  },
  created() {
  },
  methods: {
    selectMenu(idx){
      if (idx !== '2000') {
        this.curnodeid = idx;
      }
    }
  }
}
</script>