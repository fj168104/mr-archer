<template>
  <el-container>
    <el-header>
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="客户名称">
          <el-input v-model="listQuery.filters.customername" placeholder="客户名称" @keyup.enter.native="handleFilter"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
          </el-button>
          <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
              新增
          </el-button>
        </el-form-item>
      </el-form>
    </el-header>

    <el-main>
      <el-table
        :key="listQuery.tableKey"
        v-loading="listQuery.listLoading"
        :data="listQuery.list"
        border
        fit
        highlight-current-row
        style="width: 100%;"
      >
        <el-table-column label="ID" prop="id" align="center" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>

        <el-table-column label="客户名称" width="300">
          <template slot-scope="scope">
            <span>{{ scope.row.customername }}</span>
          </template>
        </el-table-column>

        <el-table-column label="业务品种" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.businesstypename }}</span>
          </template>
        </el-table-column>

        <el-table-column label="币种" width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.currency | showCodeName(codemap.Currency)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="申请金额（元）" align="right" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.money | toThousandFilter}}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建人" width="120">>
          <template slot-scope="scope">
            <span>{{ scope.row.createusername }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建机构" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.createorgname }}</span>
          </template>
        </el-table-column>

        <el-table-column label="创建时间" align="center" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.createtime | parseTime('{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width" fixed="right">

          <template slot-scope="scope">
            <el-button-group>
              <el-button @click="viewData(scope.row)" type="primary" size="mini">详情</el-button>
              <el-button @click="handleDelete(scope.$index,scope.row)" type="danger" size="mini">删除</el-button>
            </el-button-group>
          </template>

        </el-table-column>
      </el-table>

      <pagination v-show="listQuery.total>0" :total="listQuery.total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />
    </el-main>

    <!-- 新增窗口 start-->
    <el-dialog :title="'新增'" :append-to-body="true" :visible.sync="newDataDialogVisible">
      <el-form ref="newDataForm" :rules="rules" :model="newData" label-position="left" label-width="160px" style="width: 50%; margin-left:50px;">

        <el-form-item label="客户名称" prop="customername">
          <el-input v-model="newData.customername" :readonly="true">
            <el-button slot="append" icon="el-icon-search" @click="chooseDataDialogVisible = true"></el-button>
          </el-input>
        </el-form-item>
        
        <el-form-item label="业务品种" prop="businesstype">
          <el-select v-model="newData.businesstype" clearable placeholder="">
            <el-option
              v-for="item in BusinessTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="币种" prop="currency">
          <el-select v-model="newData.currency" clearable placeholder="">
            <el-option
              v-for="item in codemap.Currency"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="申请金额" prop="money">
          <el-input v-model="newData.money" class="finnumber">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="createData()">
            确定
          </el-button>
          <el-button @click="newDataDialogVisible = false">
            取消
          </el-button>
        </el-form-item>

      </el-form>
    </el-dialog>
    <!-- 新增窗口 end -->

    <!-- 查询客户列表窗口 start-->
    <el-dialog :title="'查询客户'" :append-to-body="true" :visible.sync="chooseDataDialogVisible" width="70%">
      <ent-choose-list @confirmChooseCustomer="confirmChooseCustomer"></ent-choose-list>
    </el-dialog>
    <!-- 查询客户列表窗口 end -->

    <!-- 详情窗口 start-->
    <el-dialog top="5vh" :visible.sync="viewDataDialogVisible" :append-to-body="true" :fullscreen="true" v-if="viewDataDialogVisible" :show-close="false">
      <template slot="title">
        <div>
          <span style="font-weight: bold;font-size: 20px;">申请详情-{{customername}}</span>
          <el-button type="primary" style="float: right;" @click="closeView()">返回</el-button>
        </div>
      </template>
      <business-apply-tab-view :applyid="applyid" :customerid="customerid" :customername="customername" @closeView="closeView"></business-apply-tab-view>
    </el-dialog>
    <!-- 详情窗口 end-->
  </el-container>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import { queryBusinessApplyList, createBusinessApply, deleteBusinessApply } from '@/api/busi/businessapply'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { queryCodeList } from '@/api/syscode'
import { queryBusinessTypeCodeList } from '@/api/config/businesstype'
import EntChooseList from '@/views/cust/ent/choose/list'
import BusinessApplyTabView from '@/views/business/apply/view/tabview'

export default {
  name: 'BusinessApplyList',
  components: { Pagination,EntChooseList,BusinessApplyTabView },
  directives: { waves },
  filters: {},
  props:{
    applyphase: String
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
          applyphase: this.applyphase,
          customername: ''
        },
        list: null
      },
      codemap : {},
      BusinessTypeList: [],
      newData: {
        customerid: '',
        customername: '',
        businesstype: '',
        currency: '',
        money: ''
      },
      rules: {
        customername: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
        businesstype: [{ required: true, message: '请输入业务品种', trigger: 'blur' }],
        currency: [{ required: true, message: '请输入币种', trigger: 'blur' }],
        money: [{ required: true, message: '请输入申请金额', trigger: 'blur' }]
      },
      newDataDialogVisible: false,
      chooseDataDialogVisible: false,
      viewDataDialogVisible: false,
      applyid: '',
      customername: '',
      customerid: ''
    }
  },
  watch: {
    applyphase: function(newv, oldv){
      this.listQuery.filters = {
        applyphase: newv,
        customername: ''
      }
      this.getList()
    }
  },
  created() {
    queryCodeList({codelist:['Currency']}).then(response => {
      this.codemap = response.data
      queryBusinessTypeCodeList({}).then(response => {
        this.BusinessTypeList = response.data
        this.getList()
      }).catch(() => {
        this.$message.info("获取数据失败！")
      })
    }).catch(() => {
      this.$message.info("获取数据失败！")
    })
  },
  methods: {
    getList() {
      this.listQuery.listLoading = true
      queryBusinessApplyList(this.listQuery).then(response => {
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
    resetNewData() {
      this.newData = {
        customerid: '',
        customername: '',
        businesstype: '',
        currency: '',
        money: ''
      }
    },
    handleCreate() {
      this.resetNewData()
      this.newDataDialogVisible = true
      this.$nextTick(() => {
        this.$refs['newDataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['newDataForm'].validate((valid) => {
        if (valid) {
          createBusinessApply(this.newData).then((res) => {
            this.newDataDialogVisible = false
            this.getList()
            this.$notify({
              title: 'Success',
              message: '新增成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    //删除
    handleDelete(idx, row) {
      this.$confirm('您确定要永久删除该信息？', '提示', confirm).then(() => {
        deleteBusinessApply(row.id).then(res => {
          this.$message.success("删除成功")
          this.getList()
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    viewData(row) {
      this.applyid = row.id;
      this.customerid = row.customerid;
      this.customername = row.customername;
      this.viewDataDialogVisible = true;
    },
    closeView(){
      this.viewDataDialogVisible = false;
    },
    confirmChooseCustomer(cust){
      this.newData.customerid = cust.id
      this.newData.customername = cust.name
      this.chooseDataDialogVisible = false;
    }
  }
}
</script>