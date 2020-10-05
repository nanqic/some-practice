<template>
  <div>
    <div style="margin: 20px;"></div>
    <el-form :label-position="labelPosition" label-width="80px" :rules="rules" :model="form" ref="form" style="width: 60%">
      <el-form-item label="书名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="form.author"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('form')">保存修改</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  data() {
    return {
      labelPosition: 'left',
      form: {
        name: '',
        author: ''
      },
      rules: {
        name: [
          {required: true, message: "请输入书名", trigger: 'blur'},
          {min: 3,max: 30}
        ],
        author: [
          {required: true, message: "请输作者名", trigger: 'blur'},
          {min: 3,max: 30}
        ]
      }
    };
  },
  methods: {
    onSubmit(form) {
      const _this = this
      this.$refs[form].validate((valid) => {
        if (valid) {
          axios.put('http://localhost:8181/book/update', this.form).then((resp) => {
            // console.log(resp)
            _this.updateSuccess()
          })
        } else {
          return false;
        }
      });
    },
    resetForm(form) {
      this.$refs[form].resetFields()
    },
    updateSuccess() {
      this.$alert(this.form.name+' 修改图书成功！', '执行结果', {
        confirmButtonText: '确定',
        callback: action => {
          this.$router.push('/list')
        }
      });
    }
  },
  created() {
    // console.log(this.$route.query.id)
    const _this = this
    axios.get("http://localhost:8181/book/findById/"+this.$route.query.id).then((resp)=>{
      _this.form = resp.data
    })
  }
}
</script>