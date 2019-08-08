<template>

  <div class="demo-image">
    <div class="block" v-for="item in listdata.records" :key="item.id" v-loading="dataLoading">
      <el-image
        style="width: 148px; height: 148px; float:left"
        :src="item.fileurl"
        :fit="'fill'"></el-image>
      <span class="demonstration">{{ item.filename }}</span>
    </div>
    <div class="block">
      <el-upload
        list-type="picture-card">
        <i class="el-icon-plus"></i>
      </el-upload>
    </div>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFilelistRecordList } from '@/api/filelist/filelistrecord'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'FilelistRecordImgList',
  components: { Pagination },
  directives: { waves },
  filters: {},
  props:{
    belongtype: String,
    belongid: String,
    configid: String,
    treeid: String,
    nodeid: String
  },
  data() {
    return {
      dataLoading: false,
      listdata:[],
      filters: {
        configid: this.configid,
        treeid: this.treeid,
        nodeid: this.nodeid
      }
    }
  },
  watch:{
    nodeid: function(newv,oldv) {
      this.filters.nodeid = newv
      this.getData()
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      this.dataLoading = true
      queryFilelistRecordList({filters: this.filters}).then(response => {
        this.listdata = response.data
        this.dataLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.dataLoading = false
      })
    }
  }
}
</script>