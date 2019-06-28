<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="名称">
          <el-input v-model="listQuery.filters.name" placeholder="名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table
      :key="listQuery.tableKey"
      v-loading="listQuery.listLoading"
      :data="listQuery.list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column label="名称">
        <template slot-scope="scope">
          <a @click="viewData(scope.row)">{{ scope.row.name }}</a>
        </template>
      </el-table-column>

      <el-table-column label="财报数据类型">
        <template slot-scope="scope">
          <span>{{ scope.row.findatatype | showCodeName(codemap.FinDataType)}}</span>
        </template>
      </el-table-column>

      <el-table-column label="分栏数">
        <template slot-scope="scope">
          <span>{{ scope.row.cols }}</span>
        </template>
      </el-table-column>

      <el-table-column label="描述">
        <template slot-scope="scope">
          <span>{{ scope.row.configdesc }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="130" class-name="small-padding fixed-width" fixed="right">

        <template slot-scope="scope">
          <!-- <el-tooltip content="详情" placement="top">
            <el-button @click="viewData(scope.row)" type="primary"></el-button>
          </el-tooltip> -->
          <el-tooltip content="确认选择" placement="top">
            <el-button @click="$emit('confirmChooseConfig', scope.row.id)" type="primary">确认选择</el-button>
          </el-tooltip>
        </template>

      </el-table-column>
    </el-table>

    <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 详情窗口 start-->
    <el-dialog top="5vh" :visible.sync="viewDataDialogVisible" :fullscreen="true" v-if="viewDataDialogVisible" :show-close="false">
      <template slot="title">
        <div>
          <span style="font-weight: bold;font-size: 20px;">详情-{{curname}}</span>
          <el-button type="primary" style="float: right;" @click="closeView()">返回</el-button>
        </div>
      </template>
      <fin-config-view :curid="curid" :curcols="curcols" @closeView="closeView"></fin-config-view>
    </el-dialog>
    <!-- 详情窗口 end-->
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryFinConfigList, createFinConfig, deleteFinConfig } from '@/api/fin/finconfig'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import FinConfigView from '@/views/fin/config/finconfigview'
import { queryCodeList } from '@/api/syscode'

export default {
  name: 'ChooseFinConfigList',
  components: { Pagination,FinConfigView },
  directives: { waves },
  filters: {},
  data() {
    return {
      listQuery: {
        tableKey: 0,
        listLoading: true,
        page: 1,
        total: 0,
        limit: 10,
        filters:{
          name: ''
        },
        list: null
      },
      codemap : {},
      viewDataDialogVisible: false,
      curid: '',
      curcols: 0,
      curname: ''
    }
  },
  created() {
    queryCodeList({codelist:['FinDataType']}).then(response => {
      this.codemap = response.data
      this.getList()
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryFinConfigList(this.listQuery).then(response => {
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
    viewData(row) {
      this.curid = row.id;
      this.curcols = row.cols;
      this.curname = row.name;
      this.viewDataDialogVisible = true;
    },
    closeView(){
      this.viewDataDialogVisible = false;
    }
  }
}
</script>