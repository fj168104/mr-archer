<template>
  <el-row>
    <el-col :span="24">
      <FilelistTreeList :configid="configid" ></FilelistTreeList>
    </el-col>
  </el-row>
</template>

<script>
import FilelistTreeList from '@/views/filelist/tree/filelisttreelist'

export default {
  name: 'FilelistConfigView',
  components: { FilelistTreeList },
  directives: {  },
  filters: {},
  props:{
    configid: String
  },
  data() {
    return {
    }
  },
  created() {
  },
  methods: {
  }
}
</script>