<template>
  <div class="post-product">
    <div>
      <div>
        <p>商品名</p>
        <input
          class="dialog-input"
          v-model="product.name"
          placeholder="商品名"
        />
      </div>
      <div>
        <p>新旧程度</p>
        <el-input-number
          v-model="oldLevel"
          @change="handleChange"
          :min="1"
          :max="10"
          label="新旧程度"
        ></el-input-number>
      </div>
      <div>
        <p>价格</p>
        <input
          class="dialog-input"
          v-model="product.price"
          placeholder="价格"
        />
      </div>
      <div>
        <p>是否支持砍价</p>
        <el-switch
          v-model="product.isBargain"
          active-text="支持砍价"
          inactive-text="不支持砍价"
        >
        </el-switch>
      </div>
      <div>
        <span class="post" @click="postProduct">发布商品</span>
      </div>
    </div>
    <div>
      <div>
        <p>产品图</p>
        <el-upload
          action="http://localhost:21090/api/campus-product-sys/v1.0/file/upload"
          list-type="picture-card"
          :on-success="handlePictureCardSuccess"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="" />
        </el-dialog>
      </div>
      <div>
        <p>所属商品类别</p>
        <span
          :style="{
            backgroundColor:
              categorySelected.id === category.id ? 'rgb(76, 147, 209)' : '',
            color: categorySelected.id === category.id ? 'rgb(244,244,244)' : ''
          }"
          class="category-item"
          @click="categoryClick(category)"
          v-for="(category, index) in categoryList"
          :key="index"
        >
          {{ category.name }}
        </span>
      </div>
      <div>
        <p>库存</p>
        <el-input-number
          v-model="inventory"
          @change="handleInventoryChange"
          :min="1"
          :max="10000"
          label="新旧程度"
        ></el-input-number>
      </div>
      <div>
        <Editor
          height="calc(100vh - 400px)"
          :receiveContent="product.detail"
          @on-receive="onReceive"
        />
      </div>
    </div>
  </div>
</template>
<script>
import Editor from "@/components/Editor";
export default {
  components: { Editor },
  name: "PostProduct",
  data() {
    return {
      oldLevel: 9, // 默认九成新
      inventory: 1, // 默认库存
      product: {},
      categorySelected: {}, // 当前选中的商品类别
      dialogImageUrl: "",
      dialogVisible: false,
      coverList: [],
      categoryList: []
    };
  },
  created() {
    this.fetchCategoryList();
  },
  methods: {
    /**
     * 发布商品
     */
    postProduct() {
      if (this.product.name === undefined) {
        this.$notify.info({
          duration: 1000,
          title: "填写提醒",
          message: "商品标题不能为空"
        });
        return;
      }
      if (this.product.price === undefined) {
        this.$notify.info({
          duration: 1000,
          title: "填写提醒",
          message: "商品价格不能为空"
        });
        return;
      }
      if (this.coverList.length === 0) {
        this.$notify.info({
          duration: 1000,
          title: "上传提醒",
          message: "请上传封面"
        });
        return;
      }
      this.product.coverList = this.coverList.join(",");
      // 商品封面需要处理
      this.$axios
        .post("/product/save", this.product)
        .then(res => {
          const { data } = res;
          if (data.code === 200) {
            this.$notify.success({
              title: "发布操作",
              message: data.msg
            });
            // 后续清除之后，跳转我的商品页面
            // this.product = {};
          }
        })
        .catch(error => {
          this.$notify.error({
            title: "发布操作",
            message: error
          });
          console.log("新增商品异常:", error);
        });
    },
    /**
     * 富文本编辑器回调内容
     * @param {*} detail
     */
    onReceive(detail) {
      this.product.detail = detail;
    },
    /**
     * 商品分类选中事件
     * @param {*} category 商品分类
     */
    categoryClick(category) {
      this.categorySelected = category;
      this.product.categoryId = category.id;
    },
    /**
     * 查询商品类别
     */
    fetchCategoryList() {
      this.$axios
        .post("/category/query", {})
        .then(res => {
          const { data } = res;
          if (data.code === 200) {
            this.categoryList = data.data;
            // 默认选中第一项
            this.categoryClick(this.categoryList[0]);
          }
        })
        .catch(error => {
          console.log("查询商品类别信息异常:", error);
        });
    },
    /**
     * 新旧程度选择事件
     */
    handleChange() {
      console.log("新旧程度：", this.oldLevel);
      this.product.oldLevel = this.oldLevel;
    },
    /**
     * 库存设置事件
     */
    handleInventoryChange() {
      console.log("库存：", this.inventory);
      this.product.inventory = this.inventory;
    },
    /**
     * 封面上传成功响应事件
     * @param {*} file
     * @param {*} fileList
     */
    handlePictureCardSuccess(file, fileList) {
      this.coverList.push(file.data);
    },
    handleRemove(file, fileList) {
      this.coverList = fileList;
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    }
  }
};
</script>
<style scoped lang="scss">
.post {
  display: inline-block;
  background-color: rgb(51, 51, 51);
  margin-block: 30px;
  width: 100%;
  text-align: center;
  padding: 15px;
  box-sizing: border-box;
  color: rgb(245, 245, 245);
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
}

.post:hover {
  background-color: rgb(31, 31, 31);
}

.category-item {
  display: inline-block;
  padding: 6px 20px;
  background-color: rgb(246, 246, 246);
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
  margin: 4px;
}

.category-item:hover {
  background-color: rgb(241, 241, 241);
}

.post-product {
  display: flex;
  justify-content: left;
  gap: 30px;
}
</style>
