<template>
  <div>
    <el-table
        :data="tableData"
        stripe
        style="width: 60%">
      <el-table-column
          prop="id"
          label="编号"
          width="180">
      </el-table-column>
      <el-table-column
          prop="name"
          label="书名"
          width="180">
      </el-table-column>
      <el-table-column
          prop="author"
          label="作者">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="100">
        <template slot-scope="scope">
          <el-button @click="update(scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="deleteBook(scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="5"
        :total="total"
        @current-change="change">
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: 'BookList',
  methods:{
    change(currentPage){
      const _this = this
      axios.get('http://localhost:8181/book/find/'+currentPage+'/5').then(function(resp){
        _this.tableData = resp.data.content
        _this.total = resp.data.totalElements
      })
    },
    update(row){
      // console.log(row.id)
      this.$router.push({
        path: "/update",
        query: {
          id: row.id
        }
      })
    },
    deleteBook(row){
      const _this = this
      axios.delete("http://localhost:8181/book/deleteById/"+row.id).then(()=>{
        _this.deleteSuccess()
      })
    },
    deleteSuccess() {
      this.$alert(' 删除图书成功！', '执行结果', {
        confirmButtonText: '确定',
        callback: action => {
          window.location.reload()
        }
      });
    }
  },
  data(){
    return {
      tableData: null,
      total: null
    }
  },
  created(){
    const _this = this
    axios.get('http://localhost:8181/book/find/1/5').then(function(resp){
      _this.tableData = resp.data.content
      _this.total = resp.data.totalElements
    })
  }
}
</script>