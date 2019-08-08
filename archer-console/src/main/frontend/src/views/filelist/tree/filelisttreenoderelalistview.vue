<template>
  <el-row style="height:350px">
    <el-col :span="24">
  <!-- <el-container class="app-container">
    <el-header>
      <el-form :inline="true" class="demo-form-inline filter-container">
        <el-form-item label="名称">
          <el-input v-model="listQuery.filters.name" placeholder="名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
        </el-form-item>
      </el-form>
    </el-header>

    <el-main> -->
      <el-table
        :key="listQuery.tableKey"
        v-loading="listQuery.listLoading"
        :data="listQuery.list"
        border
        fit
        highlight-current-row
        style="width: 100%;"
        @row-click="chooseNode"
      >

        <el-table-column label="节点名称">
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>

        <el-table-column label="文件类型" align="center" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.filetype | showCodeName(codemap.FileType)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="数量限制" align="center" width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.count > 0">{{ scope.row.count}}</span>
          </template>
        </el-table-column>

        <el-table-column label="是否必输" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.isrequired | showCodeName(codemap.IsNot)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="关联是否可用" align="center" width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.isinuse | showCodeName(codemap.IsNot)}}</span>
          </template>
        </el-table-column>

        <!-- <el-table-column label="节点是否可用" align="center" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.nodeisinuse | showCodeName(codemap.IsNot)}}</span>
          </template>
        </el-table-column> -->
      </el-table>

      <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    <!-- </el-main>
  </el-container> -->
    </el-col>

    <el-col :span="24">
      <el-divider content-position="left">{{curname}}</el-divider>
      <FilelistRecordImgList v-if="curid && curfiletype == '01'" :belongtype="belongtype" :belongid="belongid" :configid="configid" :treeid="treeid" :nodeid="curid"></FilelistRecordImgList>
    </el-col>
  </el-row>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFilelistTreeNodeRelaList } from '@/api/filelist/filelisttreenoderela'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'
import FilelistRecordImgList from '@/views/filelist/record/filelistrecordimglist'

export default {
  name: 'FilelistTreeNodeRelaListView',
  components: { Pagination,FilelistRecordImgList },
  directives: { waves },
  filters: {},
  props:{
    belongtype: String,
    belongid: String,
    configid: String,
    treeid: String
  },
  data() {
    return {
      listQuery: {
        tableKey: 0,
        listLoading: true,
        page: 1,
        total: 0,
        limit: 10,
        filters:{
          configid: this.configid,
          treeid: this.treeid,
          name: ''
        },
        list: null
      },
      codemap : {},
      curid: '',
      curfiletype: '',
      curname: ''
    }
  },
  watch:{
    treeid: function(newv, oldv){
      this.listQuery.filters.treeid = newv
      this.getList()
    }
  },
  created() {
    queryCodeList({codelist:['IsNot','FileType']}).then(response => {
      this.codemap = response.data
      if (this.treeid) {
        this.getList()
      } else {
        this.listQuery.listLoading = false
      }
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryFilelistTreeNodeRelaList(this.listQuery).then(response => {
        this.listQuery.list = response.data.records
        this.listQuery.total = response.data.total
        this.listQuery.listLoading = false
      }).catch(() => {
        this.$message.info("获取数据失败！")
        this.listQuery.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    chooseNode(row,column,event){
      this.curid = row.id
      this.curfiletype = row.filetype
      this.curname = row.name
    }
  }
}
</script>