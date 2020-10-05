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
        <el-button type="primary" @click="onSubmit('form')">立即创建</el-button>
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
          axios.post('http://localhost:8181/book/save', this.form).then((resp) => {
            // console.log(resp)
          })
          _this.addSuccess()
        } else {
          return false;
        }
      });
    },
    resetForm(form) {
      this.$refs[form].resetFields()
    },
    addSuccess() {
      this.$alert(this.form.name+' 添加图书成功！', '执行结果', {
        confirmButtonText: '确定',
        callback: action => {
          this.$router.push('/list')
        }
      });
    }
  }
}
</script>